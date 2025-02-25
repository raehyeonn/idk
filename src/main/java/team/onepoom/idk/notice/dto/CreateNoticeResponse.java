package team.onepoom.idk.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateNoticeResponse {

    private long id;

    @Builder
    public CreateNoticeResponse(long id) {
        this.id = id;
    }
}

