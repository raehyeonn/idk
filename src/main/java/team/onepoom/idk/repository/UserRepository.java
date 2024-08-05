package team.onepoom.idk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.onepoom.idk.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join fetch u.roles r where u.email = :email")
    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);
}
