package team.onepoom.idk.domain.notice;

public class NoticeNotFoundException extends RuntimeException {
    public NoticeNotFoundException() {
        super("해당하는 공지사항이 존재하지 않습니다.");
    }
}
