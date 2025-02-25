package team.onepoom.idk.member.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.NotFoundException;
import team.onepoom.idk.member.domain.Role;
import team.onepoom.idk.member.dto.MemberInfoResponse;
import team.onepoom.idk.member.exception.IncorrectCurrentPasswordException;
import team.onepoom.idk.member.exception.NicknameAlreadyExistsException;
import team.onepoom.idk.member.exception.SameNicknameException;
import team.onepoom.idk.member.converter.DtoToEntityConverter;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.dto.ChangeNicknameRequest;
import team.onepoom.idk.member.dto.ChangePasswordRequest;
import team.onepoom.idk.member.dto.RegisterMemberRequest;
import team.onepoom.idk.member.exception.SamePasswordException;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.security.cookie.CookieService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CookieService cookieService;

    @Transactional
    public void createMember(RegisterMemberRequest registerMemberRequest, HttpServletResponse response) {
        String encodedPassword = passwordEncoder.encode(registerMemberRequest.getPassword());
        Member member = DtoToEntityConverter.toEntity(registerMemberRequest, encodedPassword);
        memberRepository.save(member);

        cookieService.removeRefreshTokenCookie(response);
    }

    private Member getAuthenticatedMember(UserDetails userDetails) {
        // UserDetails에서 이메일을 추출하여 Member 엔티티를 조회
        String email = userDetails.getUsername();  // UserDetails에서 username은 이메일일 경우 많음
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("인증된 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void changeNickname(UserDetails userDetails, ChangeNicknameRequest changeNicknameRequest) {
        Member member = getAuthenticatedMember(userDetails);
        String newNickname = changeNicknameRequest.getChangeNickname();
        boolean existNickname = repository.existsByNickname(newNickname);

        if(existNickname) {
            throw new NicknameAlreadyExistsException("이미 사용중인 닉네임입니다.");
        }

        if(member.getNickname().equals(newNickname)) {
            throw new SameNicknameException("기존 닉네임과 동일합니다.");
        }

        member.changeNickname(newNickname);
        memberRepository.save(member);
    }

    @Transactional
    public void changePassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) {
        Member member = getAuthenticatedMember(userDetails);
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        if(!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new IncorrectCurrentPasswordException("현재 비밀번호가 일치하지 않습니다.");
        }

        if(passwordEncoder.matches(newPassword, member.getPassword())) {
            throw new SamePasswordException("현재 비밀번호와 동일합니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        member.changePassword(encodedPassword);
        memberRepository.save(member);
    }


    @Transactional
    public void suspendMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

        if (!member.getRole().equals(Role.SUSPENDED)) {
            member.suspend();
        }
    }

    public MemberInfoResponse getMyInfo(CustomUserDetails customUserDetails) {
        long id = customUserDetails.getId();
        String email = customUserDetails.getUsername();
        String nickname = customUserDetails.getNickname();

        return new MemberInfoResponse(id, email, nickname);
    }

}
