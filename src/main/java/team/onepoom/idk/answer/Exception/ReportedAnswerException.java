package team.onepoom.idk.answer.Exception;

public class ReportedAnswerException extends RuntimeException {

    public ReportedAnswerException(long id) {
        super("가려진 답변은 수정할 수 없습니다. id: " + id);
    }
}
