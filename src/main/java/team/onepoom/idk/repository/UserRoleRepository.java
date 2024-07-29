package team.onepoom.idk.repository;

import java.util.LinkedHashSet;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.domain.user.UserRole;

interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    LinkedHashSet<UserRole> findByUserEmail(String userEmail);
    LinkedHashSet<UserRole> findByUserId(Long userId);
}
