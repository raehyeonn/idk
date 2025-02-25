package team.onepoom.idk.answerReport.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAnswerReportRequest {

    @Positive
    private long answerId;

    @Positive
    private long reportReasonId;

}
