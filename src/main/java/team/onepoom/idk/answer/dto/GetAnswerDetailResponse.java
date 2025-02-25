package team.onepoom.idk.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAnswerDetailResponse {

    private long id;
    private String content;
    private boolean isSelected;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
