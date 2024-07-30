package team.onepoom.idk.common.exception;

// 404
public class NoticeNotFoundException extends NotFoundException {
    public NoticeNotFoundException() {
        super("해당하는 공지사항이 존재하지 않습니다.");
    }
}
