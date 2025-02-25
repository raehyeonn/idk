package team.onepoom.idk.reportReason.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AllReportReasonsResponse {

    private long id;
    private String content;

}
