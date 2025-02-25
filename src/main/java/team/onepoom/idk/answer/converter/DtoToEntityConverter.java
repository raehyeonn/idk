package team.onepoom.idk.answer.converter;

import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.security.jwt.CustomUserDetails;

public class DtoToEntityConverter {

    public static Answer toEntity(Question question, CustomUserDetails customUserDetails, CreateAnswerRequest createAnswerRequest) {
        return Answer.builder()
        .question(question)
        .writer(customUserDetails.getMember())
        .content(createAnswerRequest.getContent())
        .build();
    }

}
