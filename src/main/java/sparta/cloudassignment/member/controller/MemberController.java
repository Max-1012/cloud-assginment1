package sparta.cloudassignment.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.cloudassignment.global.common.ApiResponse;
import sparta.cloudassignment.global.common.aop.Logging;
import sparta.cloudassignment.member.dto.request.CreateMemberRequest;
import sparta.cloudassignment.member.dto.response.GetMemberResponse;
import sparta.cloudassignment.member.service.MemberService;
import sparta.cloudassignment.member.dto.response.FileDownloadUrlResponse;
import sparta.cloudassignment.member.dto.response.FileUploadResponse;

import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;


    // 팀원 정보 저장
    @Logging
    @PostMapping()
    public ResponseEntity<ApiResponse<GetMemberResponse>> saveMember(
            @Valid @RequestBody CreateMemberRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(memberService.saveMember(request)));
    }

    // 팀원 조회
    @Logging
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetMemberResponse>> getMember(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(ApiResponse.success(memberService.getMember(id)));
    }

    // 팀원 프로필 사진 저장

    @Logging
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<FileUploadResponse> uploadMemberImage(
            @RequestParam Long id,
            @RequestParam("file") MultipartFile file
    ) {
        String key = memberService.upload(id,file);
        return ResponseEntity.ok(new FileUploadResponse(key));
    }

    // 팀원 프로필 사진 조회(다운로드)
    @Logging
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<FileDownloadUrlResponse> getMemberImageUrl(
            @RequestParam Long id,
            @RequestParam String key
    ){
        URL url = memberService.getDownloadUrl(id,key);
        return ResponseEntity.ok(new FileDownloadUrlResponse(url.toString()));
    }
}

// 로그 -> slf4j aop