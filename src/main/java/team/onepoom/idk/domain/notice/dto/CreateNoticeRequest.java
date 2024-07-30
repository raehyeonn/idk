package team.onepoom.idk.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.notice.Notice;

@AllArgsConstructor
public class CreateNoticeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Notice toEntity(Provider provider) {
        return new Notice(provider, title, content);
    }
}
