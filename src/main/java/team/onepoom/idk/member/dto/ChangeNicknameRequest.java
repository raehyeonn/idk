package team.onepoom.idk.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChangeNicknameRequest {

    @NotBlank(message = "닉네임 변경 시 새로운 닉네임 입력은 필수 사항입니다.")
    @Size(max = 10, message = "닉네임은 최소 1자, 최대 10자까지 입력 가능합니다.")
    private String changeNickname;

}
