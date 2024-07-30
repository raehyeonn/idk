package team.onepoom.idk.common.exception;

public class AnswerNotFoundException extends NotFoundException {

    public AnswerNotFoundException(long id) {
        super("존재하지 않는 답변입니다. id: " + id);
    }
}
