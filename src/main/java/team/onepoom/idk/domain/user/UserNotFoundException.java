package team.onepoom.idk.domain.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(String email) {
        super("이메일이 "+email+"인 사용자가 존재하지 않습니다.");
    }
}
