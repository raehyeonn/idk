package team.onepoom.idk.domain.answer.dto;

import java.time.ZonedDateTime;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;

public record AnswerResponse(
    long answerId,
    Provider writer,
    String content,
    boolean isSelected,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt
) {

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
            answer.getId(),
            answer.getWriter().toProvider(),
            answer.getContent(),
            answer.isSelected(),
            answer.getCreatedAt(),
            answer.getUpdatedAt()
        );
    }
}