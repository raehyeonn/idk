package team.onepoom.idk.notice.exception;

import team.onepoom.idk.common.exception.NotFoundException;

public class NoticeNotFoundException extends NotFoundException {

    public NoticeNotFoundException(long id) {
        super("공지사항을 찾을 수 없습니다. [id: " + id + "]");
    }

}
