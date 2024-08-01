package team.onepoom.idk.common.exception;

public class SelectionConflictException extends ConflictException{

    public SelectionConflictException() {
        super("이미 채택된 답변이 존재합니다.");
    }
}
