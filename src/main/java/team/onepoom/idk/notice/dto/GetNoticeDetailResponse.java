package team.onepoom.idk.notice.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.member.dto.WriterDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNoticeDetailResponse {

    private long id;
    private WriterDto writer;
    private String title;
    private String content;
    private int viewCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}

