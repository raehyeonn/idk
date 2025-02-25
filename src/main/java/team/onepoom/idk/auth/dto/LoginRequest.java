package team.onepoom.idk.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "로그인 시 이메일 입력은 필수 사항입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "로그인 시 비밀번호 입력은 필수 사항입니다.")
    private String password;
}
