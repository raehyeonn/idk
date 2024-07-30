package team.onepoom.idk.domain.answer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import team.onepoom.idk.domain.answer.Answer;

@Getter
public record ModifyAnswerRequest(@NotBlank String content) {

}
