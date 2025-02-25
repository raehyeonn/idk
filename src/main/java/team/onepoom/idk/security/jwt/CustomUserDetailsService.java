package team.onepoom.idk.security.jwt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor

// UserDetailsService 인터페이스를 구현한 서비스 클래스
// 사용자의 이메일을 사용해서, 해당 이메일을 가진 사용자가 존재하면 이메일, 비밀번호, 권하을 가져와ㅏ 사용자 인증을 위한 필요한 정보를 userDetails객체로 반환한다
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // loadUserByUsername 메서드는 UserDetailsService 인터페이스에서 요구하는 메서드로
    // Spring Security에서 사용자 인증을 처리할 때 호출된다.

    // 로그인 시 입력한 이메일을 통해 해당 사용자를 데이터베이스에서 찾고
    // 해당 사용자에 대한 비밀번호와 권한 정보를 UserDetails 객체로 반환한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

        /*String email = member.getEmail();
        String password = member.getPassword();
        List<GrantedAuthority> grantedAuthorities = Stream.of(member.getRole()).map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());

        // User 객체는 Spring Security에서 제공하는 UserDetails 구현체!!
        // 이 객체가 Spring Security의 인증처리에 사용된다.
        // 나중에 이 객체로 사용자 인증이 성공한다면 -> ScurityContext에 저장되고, 이후 사용자는 인증된 사용자로 시스템에 접근 가능해짐
        return new User(email, password, grantedAuthorities);
*/
        return new CustomUserDetails(member);
    }
}
