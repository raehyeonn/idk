package team.onepoom.idk.domain.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.question.Question;
import team.onepoom.idk.domain.user.dto.WriterDTO;

public record MyAnswerResponse(
    long questionId,
    WriterDTO writer,
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
            new WriterDTO(question.getWriter()),
            question.getReportedAt() == null ? question.getTitle() : "신고된 게시물입니다.",
            question.getReportedAt() == null ? question.getContent() : "신고된 게시물입니다.",
            question.isSelected(),
            question.getAnswers().size(),
            AnswerResponse.from(answer),
            question.getCreatedAt(),
            answer.getUpdatedAt()
        );
    }
}