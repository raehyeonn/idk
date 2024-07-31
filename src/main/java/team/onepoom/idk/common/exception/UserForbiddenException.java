package team.onepoom.idk.common.exception;

public class UserForbiddenException extends ForbiddenException {
    public UserForbiddenException() {
        super("권한이 없습니다.");
    }
}
