package team.onepoom.idk.questrionReport.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionReportResponse {

    private long questionReportId;
    private long questionId;
    private String writer;
    private String title;
    private String content;
    private String reporter;
    private String reportReason;
    private ZonedDateTime createdAt;

}
