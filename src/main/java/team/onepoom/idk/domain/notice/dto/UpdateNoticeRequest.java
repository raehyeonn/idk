package team.onepoom.idk.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import team.onepoom.idk.domain.notice.Notice;

public class UpdateNoticeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Notice toEntity() {
        return new Notice(this.title, this.content);
    }

}
