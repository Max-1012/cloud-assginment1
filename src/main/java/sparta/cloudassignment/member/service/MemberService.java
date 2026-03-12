package sparta.cloudassignment.member.service;

import io.awspring.cloud.s3.S3Template;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import sparta.cloudassignment.global.error.CommonError;
import sparta.cloudassignment.global.error.CommonException;
import sparta.cloudassignment.member.dto.request.CreateMemberRequest;
import sparta.cloudassignment.member.dto.response.GetMemberResponse;
import sparta.cloudassignment.member.entity.Member;
import sparta.cloudassignment.member.repository.MemberRepository;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    private static final Duration PRESIGNED_URL_EXPIRATION = Duration.ofDays(7);
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    // 멤버 조회
    public GetMemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.MEMBER_NOT_FOUND)
        );

        return GetMemberResponse.from(member);

    }

    // 멤버 저장
    @Transactional
    public GetMemberResponse saveMember(CreateMemberRequest request) {
        Member savedMember = memberRepository.save(Member.builder()
                .name(request.name())
                .age(request.age())
                .mbti(request.mbti())
                .build());
        return GetMemberResponse.from(savedMember);
    }

    // 멤버 이미지 업로드
    @Transactional
    public String upload(Long id,MultipartFile file) {
        // 멤버가 있는지 먼저 확인
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.MEMBER_NOT_FOUND)
        );

        try {
            String key = "uploads/member-" + id + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            s3Template.upload(bucket, key, file.getInputStream());
            member.uploadMemberImage(key);
            memberRepository.save(member); // 명시적으로 저장
            return key;
        } catch (Exception e) {
            throw new CommonException(CommonError.FAIL_TO_UPLOAD_FILE_ERROR);
        }

    }

    // 멤버 이미지 url 불러오기
    public URL getDownloadUrl(Long id, String key) throws CommonException {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.MEMBER_NOT_FOUND)
        );

        if (!key.equals(member.getProfileImageKey())) {
            throw new CommonException(CommonError.INVALID_IMAGE_KEY);
        }

        try {
            return s3Template.createSignedGetURL(bucket, key, PRESIGNED_URL_EXPIRATION);
        } catch (SdkClientException e) {
            throw new CommonException(CommonError.FAIL_TO_CREATE_PRESIGNED_URL);
        }
    }
}
