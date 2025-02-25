package team.onepoom.idk.notice.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecentNoticesResponse {

    private long id;
    private String title;
    private ZonedDateTime createdAt;

}
