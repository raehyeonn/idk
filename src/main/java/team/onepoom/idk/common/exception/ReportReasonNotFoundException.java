package team.onepoom.idk.common.exception;

public class ReportReasonNotFoundException extends NotFoundException{
    public ReportReasonNotFoundException() {
        super("해당하는 신고사유가 존재하지 않습니다.");
    }
}
