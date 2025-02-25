package team.onepoom.idk.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateQuestionRequest {

    private String title;
    private String content;

}
