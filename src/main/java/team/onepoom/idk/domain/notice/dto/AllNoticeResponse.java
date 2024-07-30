package team.onepoom.idk.domain.notice.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.notice.Notice;

// 필수 2개 꼭 붙여라..
@Getter
@NoArgsConstructor
public class AllNoticeResponse {
    private Provider writer;
    private String title;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static AllNoticeResponse from(Notice notice) {
        AllNoticeResponse dto = new AllNoticeResponse();
        dto.writer = notice.getWriter().toProvider();
        dto.title = notice.getTitle();
        dto.createdAt = notice.getCreatedAt();
        dto.updatedAt = notice.getUpdatedAt();
        return dto;
    }
}
