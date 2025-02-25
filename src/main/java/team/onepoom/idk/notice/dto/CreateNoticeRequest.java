package team.onepoom.idk.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNoticeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
