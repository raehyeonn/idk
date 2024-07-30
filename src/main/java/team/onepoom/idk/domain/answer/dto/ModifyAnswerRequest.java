package team.onepoom.idk.domain.answer.dto;

import jakarta.validation.constraints.NotBlank;

public record ModifyAnswerRequest(@NotBlank String content) {

}
