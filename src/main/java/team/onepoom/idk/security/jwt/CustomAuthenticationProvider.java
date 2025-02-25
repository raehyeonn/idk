package team.onepoom.idk.security.jwt;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

// Spring Security에서 인증 과정을 처리하는 AuthenticationProvider의 구현체
// 사용자 인증을 담당하며, 사용자가 제공한 인증정보를 검사하여 인증을 수행한다.
// 인증된 경우 인증 객체를 반환한다.

// 이 클래스는 Spring Security의 인증 과정에서 자동으로 호출되도록 설정


// AuthenticationProvider는 Spring Security에서 인증을 처리하는 핵심 인터페이스입니다.
// CustomAuthenticationProvider는 이 인터페이스를 구현하여, 사용자 인증을 어떻게 처리할지 정의합니다.
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // CustomUserDetailsService에서 정의된 로직에 따라 사용자의 이메일을 기반으로 UserDetails 객체 조회 가능
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    // 사용자가 제공한 인증 정보(이메일, 비밀번호)를 검증하는 역할을 하는 메서드
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();


        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        // 이메일을 기반으로 사용자의 정보를 DB에서 조회한다.
        // UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 조회한 사용자 정보에서 저장된 비밀번호 가져오기
        String storedPassword = customUserDetails.getPassword();

        // 입력된 비밀번호와 저장된 비밀번호 비교
        if (passwordEncoder.matches(password, storedPassword)) {
            // 일치하면 인증 성공
            List<GrantedAuthority> authorities = new ArrayList<>(customUserDetails.getAuthorities());

            // UsernamePasswordAuthenticationToken은 Spring Security에서 아이디(사용자명)와 비밀번호로 인증을 처리할 때 사용되는 기본 인증 토큰입니다.
            // 인증이 성공적으로 이루어지면, UsernamePasswordAuthenticationToken 객체가 생성되어 반환됩니다.
            // 이 객체는 사용자 정보를 담고 있으며, **SecurityContext**에 저장되어 이후의 보안 컨텍스트에 사용됩니다.
            return new UsernamePasswordAuthenticationToken(customUserDetails, password, authorities);
        } else {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    // AuthenticationProvider가 어떤 인증 방식을 처리할 수 있는지 확인하는 메서드입니다.

    // UsernamePasswordAuthenticationToken 클래스를 지원하는지 여부를 확인합니다. 이 클래스는 일반적으로 아이디와 비밀번호를 사용한 인증에서 사용됩니다.
    // UsernamePasswordAuthenticationToken 타입의 인증 요청을 처리할 수 있다는 것을 Spring Security에게 알려주는 역할을 합니다.
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
