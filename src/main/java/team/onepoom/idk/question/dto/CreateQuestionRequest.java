package team.onepoom.idk.question.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class CreateQuestionRequest {

    @NotBlank
    @Length(min = 1, max = 50)
    private String title;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String content;

}
