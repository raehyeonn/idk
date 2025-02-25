package team.onepoom.idk.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.answer.dto.AnswerDTO;
import team.onepoom.idk.member.dto.WriterDto;
import team.onepoom.idk.question.domain.Question;

@Getter
@NoArgsConstructor
public class GetQuestionDetailResponse {

    private long id;
    private WriterDto writer;
    private String title;
    private String content;
    private List<AnswerDTO> answers;
    private boolean hasBestAnswer;
    private long answerCount;
    private long viewCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime blindedAt;

    @QueryProjection
    public GetQuestionDetailResponse(Question question) {
        this.id = question.getId();
        this.writer = new WriterDto(question.getWriter());
        this.title = question.getBlindedAt() == null ? question.getTitle() : "가려진 게시물입니다.";
        this.content = question.getBlindedAt() == null ? question.getContent() : "가려진 게시물입니다.";
        this.answers = question.getAnswers().stream().map(AnswerDTO::new).collect(Collectors.toList());
        this.hasBestAnswer = question.isHasBestAnswer();
        this.answerCount = question.getAnswers().size();
        this.viewCount = question.getViewCount();
        this.createdAt = question.getCreatedAt();
        this.updatedAt = question.getUpdatedAt();
        this.blindedAt = question.getBlindedAt();
    }

}
