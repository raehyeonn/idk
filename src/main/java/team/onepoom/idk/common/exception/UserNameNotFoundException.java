package team.onepoom.idk.common.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNameNotFoundException extends UsernameNotFoundException {

    public UserNameNotFoundException(String email) {
        super("이메일이 " + email + "인 사용자가 존재하지 않습니다.");
    }
}
