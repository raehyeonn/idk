package team.onepoom.idk.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.onepoom.idk.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String mail);
}
