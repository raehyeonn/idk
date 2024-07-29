package team.onepoom.idk.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ANONYMOUS, USER, ADMIN, SUSPEND;

    @Override
    public String getAuthority() {
        return "ROLE_"+name();
    }
}
