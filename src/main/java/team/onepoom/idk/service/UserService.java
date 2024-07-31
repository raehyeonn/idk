package team.onepoom.idk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.user.CreateUser;
import team.onepoom.idk.domain.user.User;
import team.onepoom.idk.domain.user.UserNotFoundException;
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
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
    //탈퇴시 탈퇴시간 등록, 유저권한 삭제
    @Transactional
    public void delete(Provider provider) {
        findUser(provider.id()).delete(provider);
    }

    private User findUser(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new team.onepoom.idk.common.exception.UserNotFoundException(id));
    }

    public void suspend(Provider provider, long id) {

    }
}
