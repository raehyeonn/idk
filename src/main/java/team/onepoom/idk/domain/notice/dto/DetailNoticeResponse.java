package team.onepoom.idk.domain.notice.dto;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.notice.Notice;

@Getter
@NoArgsConstructor
public class DetailNoticeResponse {
    private long id;
    private Provider writer;
    private String title;
    private String content;
    private int views;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static DetailNoticeResponse from(Notice notice) {
        DetailNoticeResponse dto = new DetailNoticeResponse();
        dto.id = notice.getId();
        dto.writer = notice.getWriter().toProvider();
        dto.title = notice.getTitle();
        dto.content = notice.getContent();
        dto.views = notice.getViews();
        dto.createdAt = notice.getCreatedAt();
        dto.updatedAt = notice.getUpdatedAt();
        return dto;
    }
}