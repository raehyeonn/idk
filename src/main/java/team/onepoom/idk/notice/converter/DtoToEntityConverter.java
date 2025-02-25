package team.onepoom.idk.notice.converter;

import team.onepoom.idk.notice.domain.Notice;
import team.onepoom.idk.notice.dto.CreateNoticeRequest;
import team.onepoom.idk.security.jwt.CustomUserDetails;

public class DtoToEntityConverter {

    public static Notice toEntity(CustomUserDetails customUserDetails, CreateNoticeRequest createNoticeRequest) {
        return Notice.builder()
        .writer(customUserDetails.getMember())
        .title(createNoticeRequest.getTitle())
        .content(createNoticeRequest.getContent())
        .build();
    }

}
