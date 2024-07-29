package team.onepoom.idk.domain.user.dto;

import java.util.Set;
import team.onepoom.idk.domain.user.Role;

public record FindUserResponse(long id, String email, Set<Role> roles) {

}
