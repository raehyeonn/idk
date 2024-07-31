package team.onepoom.idk.domain.notice.dto;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.notice.Notice;

@Getter
@NoArgsConstructor
public class FiveNoticeResponse {
    private String title;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static FiveNoticeResponse from(Notice notice) {
        FiveNoticeResponse dto= new FiveNoticeResponse();
        dto.title = notice.getTitle();
        dto.createdAt = notice.getCreatedAt();
        dto.updatedAt = notice.getUpdatedAt();
        return dto;
    }
}
