package team.onepoom.idk.domain;


import java.util.Collection;
import team.onepoom.idk.domain.user.Role;

public record Provider(long id, String email, String nickname, Collection<Role> roles){
}
