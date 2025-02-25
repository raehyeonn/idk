package team.onepoom.idk.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.member.dto.ChangeNicknameRequest;
import team.onepoom.idk.member.dto.ChangePasswordRequest;
import team.onepoom.idk.member.dto.MemberInfoResponse;
import team.onepoom.idk.member.dto.RegisterMemberRequest;
import team.onepoom.idk.member.service.MemberService;
import team.onepoom.idk.member.service.MemberStatusService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberStatusService memberStatusService;

    @PostMapping
    public ResponseEntity<String> registerMember(@Valid @RequestBody RegisterMemberRequest registerMemberRequest, HttpServletResponse httpServletResponse) {
        memberService.createMember(registerMemberRequest, httpServletResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 생성을 성공적으로 완료되었습니다.");
    }

    @PatchMapping("/profile/nickname")
    public ResponseEntity<String> changeNickname(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        memberService.changeNickname(userDetails, changeNicknameRequest);
        return ResponseEntity.ok("닉네임 변경이 성공적으로 완료되었습니다.");
    }

    @PatchMapping("/profile/password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        memberService.changePassword(userDetails, changePasswordRequest);
        return ResponseEntity.ok("비밀번호 변경이 성공적으로 완료되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal UserDetails userDetails) {
        memberStatusService.deleteMember(userDetails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MemberInfoResponse memberInfoResponse = memberService.getMyInfo(customUserDetails);
        return ResponseEntity.ok(memberInfoResponse);
    }

}
