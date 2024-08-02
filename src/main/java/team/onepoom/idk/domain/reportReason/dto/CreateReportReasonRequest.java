package team.onepoom.idk.domain.reportReason.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.reportReason.ReportReason;

@Getter
@NoArgsConstructor
public class CreateReportReasonRequest {
    @NotBlank
    private String content;

    public ReportReason toEntity() {
        return new ReportReason(content);
    }
}
