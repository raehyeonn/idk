package team.onepoom.idk.domain.answer.dto;

import java.time.ZonedDateTime;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.user.dto.WriterDTO;

public record AnswerResponse(
    long answerId,
    WriterDTO writer,
    String content,
    boolean isSelected,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt
) {

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
            answer.getId(),
            new WriterDTO(answer.getWriter()),
            answer.getContent(),
            answer.isSelected(),
            answer.getCreatedAt(),
            answer.getUpdatedAt()
        );
    }
}