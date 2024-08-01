package team.onepoom.idk.common.exception;

import org.hibernate.annotations.NotFound;

public class AnswerReportNotFoundException extends NotFoundException {
    public AnswerReportNotFoundException() {
        super("해당하는 신고 내역을 찾을 수 없습니다.");
    }
}
