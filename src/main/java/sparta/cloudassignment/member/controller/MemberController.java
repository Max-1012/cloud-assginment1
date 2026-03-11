package sparta.cloudassignment.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.cloudassignment.global.common.ApiResponse;
import sparta.cloudassignment.global.common.aop.Logging;
import sparta.cloudassignment.member.dto.request.CreateMemberRequest;
import sparta.cloudassignment.member.dto.response.GetMemberResponse;
import sparta.cloudassignment.member.service.MemberService;

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

}

// 로그 -> slf4j aop