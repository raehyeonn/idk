package team.onepoom.idk.common.exception;

import team.onepoom.idk.member.domain.Role;

public class UserRoleNotFoundException extends NotFoundException{

    public UserRoleNotFoundException(Role role) {
        super("해당 권한을 찾을 수 없습니다. " + role);
    }
}
