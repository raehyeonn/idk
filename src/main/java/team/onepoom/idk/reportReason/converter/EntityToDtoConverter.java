package team.onepoom.idk.reportReason.converter;

import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.reportReason.dto.AllReportReasonsResponse;

public class EntityToDtoConverter {

    public static AllReportReasonsResponse toAllReportReasonResponse(ReportReason reportReason) {
        return AllReportReasonsResponse.builder()
        .id(reportReason.getId())
        .content(reportReason.getContent())
        .build();
    }

}
