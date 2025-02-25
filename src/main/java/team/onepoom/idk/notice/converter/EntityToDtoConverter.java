package team.onepoom.idk.notice.converter;

import team.onepoom.idk.member.dto.WriterDto;
import team.onepoom.idk.notice.domain.Notice;
import team.onepoom.idk.notice.dto.GetAllNoticesResponse;
import team.onepoom.idk.notice.dto.GetNoticeDetailResponse;
import team.onepoom.idk.notice.dto.GetRecentNoticesResponse;

public class EntityToDtoConverter {

    public static GetAllNoticesResponse toAllNoticesResponse(Notice notice) {
        return GetAllNoticesResponse.builder()
        .id(notice.getId())
        .writer(new WriterDto(notice.getWriter()))
        .title(notice.getTitle())
        .viewCount(notice.getViewCount())
        .createdAt(notice.getCreatedAt())
        .updatedAt(notice.getUpdatedAt())
        .build();
    }

    public static GetNoticeDetailResponse toDetailNoticeResponse(Notice notice) {
        return GetNoticeDetailResponse.builder()
        .id(notice.getId())
        .writer(new WriterDto(notice.getWriter()))
        .title(notice.getTitle())
        .content(notice.getContent())
        .viewCount(notice.getViewCount())
        .createdAt(notice.getCreatedAt())
        .updatedAt(notice.getUpdatedAt())
        .build();
    }

    public static GetRecentNoticesResponse toRecentNoticeResponse(Notice notice) {
        return GetRecentNoticesResponse.builder()
        .id(notice.getId())
        .title(notice.getTitle())
        .createdAt(notice.getCreatedAt())
        .build();
    }

}
