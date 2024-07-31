package team.onepoom.idk.domain.reportReason.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.reportReason.ReportReason;

@Getter
@NoArgsConstructor
public class CreateReportReasonRequest {
    private String content;

    public ReportReason toEntity() {
        return new ReportReason(content);
    }
}
