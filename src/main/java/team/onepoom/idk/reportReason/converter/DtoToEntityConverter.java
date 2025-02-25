package team.onepoom.idk.reportReason.converter;

import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.reportReason.dto.CreateReportReasonRequest;

public class DtoToEntityConverter {

    public static ReportReason toEntity(CreateReportReasonRequest createReportReasonRequest) {
        return ReportReason.builder()
        .content(createReportReasonRequest.getContent())
        .build();
    }

}
