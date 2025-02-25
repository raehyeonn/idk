package team.onepoom.idk.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.question.domain.Question;

@Getter
@NoArgsConstructor
public class GetMyQuestionResponse {

    private long id;
    private String title;
    private String content;
    private boolean hasBestAnswer;
    private long answerCount;
    private long viewCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime blindedAt;

    @QueryProjection
    public GetMyQuestionResponse(Question question, long answerCount) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.hasBestAnswer = question.isHasBestAnswer();
        this.answerCount = answerCount;
        this.viewCount = question.getViewCount();
        this.createdAt = question.getCreatedAt();
        this.updatedAt = question.getUpdatedAt();
        this.blindedAt = question.getBlindedAt();
    }

}
