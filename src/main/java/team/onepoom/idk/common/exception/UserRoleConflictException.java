package team.onepoom.idk.common.exception;

import team.onepoom.idk.domain.user.Role;

public class UserRoleConflictException extends ConflictException{

    public UserRoleConflictException(Role role) {
        super("이미 처리된 요청입니다. " + role);
    }
}
