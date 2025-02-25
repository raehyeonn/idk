package team.onepoom.idk.member.dto;

import java.util.Set;
import team.onepoom.idk.member.domain.Role;

public record FindUserResponse(long id, String email, Set<Role> roles) {

}
