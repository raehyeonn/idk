package team.onepoom.idk.answer.converter;

import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answer.dto.GetAnswerDetailResponse;
import team.onepoom.idk.answer.dto.GetMyAnswersResponse;
import team.onepoom.idk.question.domain.Question;

public class EntityToDtoConverter {

    public static GetAnswerDetailResponse toAnswerDetailResponse(Answer answer) {
        return GetAnswerDetailResponse.builder()
        .id(answer.getId())
        .content(answer.getContent())
        .isSelected(answer.isSelected())
        .createdAt(answer.getCreatedAt())
        .updatedAt(answer.getUpdatedAt())
        .build();
    }

    public static GetMyAnswersResponse toMyAnswersResponse(Answer answer) {
        Question question = answer.getQuestion();

        return GetMyAnswersResponse.builder()
        .id(question.getId())
        .writer(question.getWriter().getNickname())
        .title(question.getTitle())
        .content(question.getContent())
        .hasBestAnswer(question.isHasBestAnswer())
        .answerCount(question.getAnswers().size())
        .createdAt(question.getCreatedAt())
        .updatedAt(question.getUpdatedAt())
        .answerDetail(toAnswerDetailResponse(answer))
        .build();
    }

}
