package team.onepoom.idk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.repository.UserRepository;
import team.onepoom.idk.domain.user.CreateUser;
import team.onepoom.idk.domain.user.User;
import team.onepoom.idk.domain.user.UserNotFoundException;

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
}
