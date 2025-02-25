package team.onepoom.idk.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateNoticeRequest {

    private String title;
    private String content;

}
