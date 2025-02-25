package team.onepoom.idk.question.converter;

import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.question.dto.CreateQuestionRequest;
import team.onepoom.idk.security.jwt.CustomUserDetails;

public class DtoToEntityConverter {

    public static Question toEntity(CustomUserDetails customUserDetails, CreateQuestionRequest createQuestionRequest) {
        return Question.builder()
        .writer(customUserDetails.getMember())
        .title(createQuestionRequest.getTitle())
        .content(createQuestionRequest.getContent())
        .build();
    }

}
