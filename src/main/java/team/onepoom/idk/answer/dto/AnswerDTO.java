package team.onepoom.idk.answer.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.member.dto.WriterDto;

@Getter
@NoArgsConstructor
public class AnswerDTO {

    private long id;
    private WriterDto writer;
    private String content;
    private boolean isSelected;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime blindedAt;

    @Builder
    @QueryProjection
    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.writer = new WriterDto(answer.getWriter());
        this.content = answer.getBlindedAt() == null ? answer.getContent() : "비공개 처리된 답변입니다.";
        this.isSelected = answer.isSelected();
        this.createdAt = answer.getCreatedAt();
        this.updatedAt = answer.getUpdatedAt();
        this.blindedAt = answer.getBlindedAt();
    }

}
