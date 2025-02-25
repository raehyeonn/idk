package team.onepoom.idk.answer.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyAnswersResponse {

    private long id;
    private String writer;
    private String title;
    private String content;
    private boolean hasBestAnswer;
    private int answerCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private GetAnswerDetailResponse answerDetail;

}
