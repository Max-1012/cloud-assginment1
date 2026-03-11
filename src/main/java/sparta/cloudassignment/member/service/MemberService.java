package sparta.cloudassignment.member.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.cloudassignment.global.error.CommonError;
import sparta.cloudassignment.global.error.CommonException;
import sparta.cloudassignment.member.dto.request.CreateMemberRequest;
import sparta.cloudassignment.member.dto.response.GetMemberResponse;
import sparta.cloudassignment.member.entity.Member;
import sparta.cloudassignment.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public GetMemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.MEMBER_NOT_FOUND)
        );

        return GetMemberResponse.from(member);

    }

    public GetMemberResponse saveMember(CreateMemberRequest request) {
        Member savedMember = memberRepository.save(Member.builder()
                .name(request.name())
                .age(request.age())
                .mbti(request.mbti())
                .build());
        return GetMemberResponse.from(savedMember);
    }
}
