package team.onepoom.idk.answer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAnswerRequest {

    @Positive
    long questionId;

    @NotBlank
    String content;

}
