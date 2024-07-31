package team.onepoom.idk.common.exception;

public class ReportReasonNotFoundException extends NotFoundException{
    public ReportReasonNotFoundException() {
        super("해당하는 공지사항이 존재하지 않습니다.");
    }
}
