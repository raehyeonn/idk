package team.onepoom.idk.domain.reportReason.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.reportReason.ReportReason;

@Getter
@NoArgsConstructor
public class AllReportReasonResponse {
    private long id;
    private String content;

    public static AllReportReasonResponse from(ReportReason reportReason) {
        AllReportReasonResponse dto = new AllReportReasonResponse();
        dto.id = reportReason.getId();
        dto.content = reportReason.getContent();
        return dto;
    }
}
