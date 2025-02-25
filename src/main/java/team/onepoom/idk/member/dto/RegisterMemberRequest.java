package team.onepoom.idk.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterMemberRequest {

    @NotBlank(message = "회원 가입 시 이메일 입력은 필수 사항입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email;

    @NotBlank(message = "회원 가입 시 비밀번호 입력은 필수 사항입니다.")
    @Size(min = 10)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,}$", message = "비밀번호는 최소 10자여야 하며, 영문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.")
    String password;

    @NotBlank(message ="회원 가입 시 닉네임 입력은 필수 사항입니다.")
    @Size(max = 10, message = "닉네임은 최소 1자, 최대 10자까지 입력 가능합니다.")
    String nickname;

}
