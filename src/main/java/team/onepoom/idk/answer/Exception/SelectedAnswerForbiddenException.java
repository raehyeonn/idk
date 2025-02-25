package team.onepoom.idk.answer.Exception;

import team.onepoom.idk.common.exception.ForbiddenException;

public class SelectedAnswerForbiddenException extends ForbiddenException {

    public SelectedAnswerForbiddenException() {
        super("채택된 답변은 수정 또는 삭제할 수 없습니다.");
    }
}
