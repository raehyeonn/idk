package team.onepoom.idk.service;

import static team.onepoom.idk.domain.user.Role.ADMIN;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.UserForbiddenException;
import team.onepoom.idk.common.exception.UserNotFoundException;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.user.CreateUser;
import team.onepoom.idk.domain.user.User;
import team.onepoom.idk.common.exception.UserNameNotFoundException;
import team.onepoom.idk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public void create(CreateUser user) {
        String encoded = encoder.encode(user.password());
        repository.save(new User(user.insertEncodedPassword(encoded)));
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
            .orElseThrow(() -> new UserNameNotFoundException(email));
    }

    //탈퇴시 탈퇴시간 등록, 유저권한 삭제
    @Transactional
    public void delete(Provider provider) {
        findUser(provider.id()).delete();
    }

    private User findUser(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    //관리자 -> 유저 정지
    @Transactional
    public void suspend(Provider provider, long id) {
        checkAdminAuthority(provider);
        findUser(id).suspend();
    }

    //관리자 -> 유저 정지해제
    @Transactional
    public void unsuspend(Provider provider, long id) {
        checkAdminAuthority(provider);
        findUser(id).unsuspend();
    }

    //관리자 권한 확인
    private void checkAdminAuthority(Provider provider) {
        if (!provider.roles().contains(ADMIN)) {
            throw new UserForbiddenException();
        }
    }
}
