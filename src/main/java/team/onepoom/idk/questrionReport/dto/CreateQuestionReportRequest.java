package team.onepoom.idk.questrionReport.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateQuestionReportRequest {

    @Positive
    private long questionId;

    @Positive
    private long reportReasonId;

}
