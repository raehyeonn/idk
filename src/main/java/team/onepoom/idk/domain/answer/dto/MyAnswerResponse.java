package team.onepoom.idk.domain.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.question.Question;

public record MyAnswerResponse(
    long questionId,
    Provider writer,
    String title,
    String content,
    @JsonProperty("isSelected") boolean selected,
    int answerCount,
    AnswerResponse myAnswer,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt
) {

    public static MyAnswerResponse from(Answer answer) {
        Question question = answer.getQuestion();
        return new MyAnswerResponse(
            question.getId(),
            question.getWriter().toProvider(),
            question.getTitle(),
            question.getContent(),
            question.isSelected(),
            0,
//            question.getAnswers().size(), 추후 적용
            AnswerResponse.from(answer),
            question.getCreatedAt(),
            answer.getUpdatedAt()
        );
    }
}