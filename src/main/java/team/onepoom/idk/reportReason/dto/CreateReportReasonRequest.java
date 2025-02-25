package team.onepoom.idk.reportReason.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReportReasonRequest {

    @NotBlank
    private String content;

}
