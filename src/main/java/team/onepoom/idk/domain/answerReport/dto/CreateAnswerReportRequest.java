package team.onepoom.idk.domain.answerReport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.answerReport.AnswerReport;
import team.onepoom.idk.domain.reportReason.ReportReason;
import team.onepoom.idk.domain.user.User;

@Getter
@NoArgsConstructor
public class CreateAnswerReportRequest {
    private long reportReasonId;
    private long answerId;

    public AnswerReport toEntity(Provider provider) {
        return new AnswerReport(provider, new ReportReason(reportReasonId), new Answer(answerId));
    }
}
