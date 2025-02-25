package team.onepoom.idk.answer.Exception;

import team.onepoom.idk.common.exception.NotFoundException;

public class AnswerNotFoundException extends NotFoundException {

    public AnswerNotFoundException(long id) {
        super("해당 답변은 이미 삭제되었거나 존재하지 않습니다. id: " + id);
    }

}
