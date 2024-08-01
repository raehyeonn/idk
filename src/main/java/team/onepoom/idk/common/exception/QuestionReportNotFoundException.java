package team.onepoom.idk.common.exception;

public class QuestionReportNotFoundException extends NotFoundException {

    public QuestionReportNotFoundException(long id) {
        super("해당하는 신고 내역을 찾을 수 없습니다. id: " + id);
    }
}
