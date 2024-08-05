package team.onepoom.idk.domain.answerReport.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answerReport.AnswerReport;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAnswerReportResponse {
    private long answerReportId;
    private long answerId;
    private String reporter;
    private Provider writer;
    private String content;
    private String reportReason;
    private ZonedDateTime createdAt;

    public static GetAnswerReportResponse from(AnswerReport answerReport) {
        GetAnswerReportResponse dto = new GetAnswerReportResponse();

        dto.answerReportId = answerReport.getId();
        dto.answerId = answerReport.getAnswer().getId();
        dto.reporter = answerReport.getReporter().getNickname();
        dto.writer = answerReport.getAnswer().getWriter().toProvider();
        dto.content = answerReport.getAnswer().getContent();
        dto.reportReason = answerReport.getReportReason().getContent();
        dto.createdAt = answerReport.getCreatedAt();

        return dto;
    }
}
