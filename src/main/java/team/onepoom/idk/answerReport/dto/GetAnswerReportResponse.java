package team.onepoom.idk.answerReport.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.member.domain.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAnswerReportResponse {

    private long answerReportId;
    private long answerId;
    private String writer;
    private String content;
    private String reporter;
    private String reportReason;
    private ZonedDateTime createdAt;

}
