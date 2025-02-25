package team.onepoom.idk.security.jwt;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.onepoom.idk.member.domain.Member;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getEmail();  // 이메일
    }

    @Override
    public String getPassword() {
        return member.getPassword();  // 비밀번호
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 추가적으로, Member의 id를 반환하는 메서드를 추가
    public Member getMember() {
        return member;  // 사용자 ID
    }

    public long getId() {
        return member.getId();
    }

    public String getNickname() {
        return member.getNickname();
    }

}
