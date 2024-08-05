package team.onepoom.idk.common.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateNicknameException extends DataIntegrityViolationException {

    public DuplicateNicknameException() {
        super("이미 사용중인 닉네임입니다.");
    }
}
