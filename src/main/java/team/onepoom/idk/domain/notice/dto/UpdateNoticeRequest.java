package team.onepoom.idk.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import team.onepoom.idk.domain.notice.Notice;

@AllArgsConstructor
public class UpdateNoticeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Notice toEntity() {
        return new Notice(this.title, this.content);
    }

}
