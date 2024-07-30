package team.onepoom.idk.domain.user;

import static team.onepoom.idk.domain.user.Role.USER;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.onepoom.idk.domain.BaseEntity;
import team.onepoom.idk.domain.Provider;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    @Column(nullable = false, length = 1000)
    private String password;
    @Column(nullable = false, length = 12, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> roles = new LinkedHashSet<>();

    private User(String email, String password, String nickname, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles.addAll(
            roles.stream().map(role -> new UserRole(this, role)).collect(Collectors.toSet()));
    }

    public User(Long id) {
        this.id = id;
    }

    public User(CreateUser user) {
        this(user.email(), user.password(), user.nickname(), Set.of(USER));
    }

    public Provider toProvider() {
        return new Provider(id, email, nickname,
            roles.stream().map(UserRole::toRole).collect(Collectors.toSet()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(UserRole::toRole).collect(Collectors.toSet());
    }

    @Override
    @Transient
    public String getUsername() {
        return email;
    }
}
