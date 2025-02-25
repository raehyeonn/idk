package team.onepoom.idk.notice.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.notice.converter.DtoToEntityConverter;
import team.onepoom.idk.notice.converter.EntityToDtoConverter;
import team.onepoom.idk.notice.domain.Notice;
import team.onepoom.idk.notice.dto.CreateNoticeResponse;
import team.onepoom.idk.notice.exception.NoticeNotFoundException;
import team.onepoom.idk.notice.dto.GetAllNoticesResponse;
import team.onepoom.idk.notice.dto.CreateNoticeRequest;
import team.onepoom.idk.notice.dto.GetNoticeDetailResponse;
import team.onepoom.idk.notice.dto.GetRecentNoticesResponse;
import team.onepoom.idk.notice.dto.UpdateNoticeRequest;
import team.onepoom.idk.notice.repository.NoticeRepository;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public CreateNoticeResponse createNotice(CustomUserDetails customUserDetails, CreateNoticeRequest createNoticeRequest) {
        Notice notice = DtoToEntityConverter.toEntity(customUserDetails, createNoticeRequest);
        noticeRepository.save(notice);
        return new CreateNoticeResponse(notice.getId());
    }

    public Notice getNoticeById(long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException(id));
    }

    @Transactional
    public void updateNotice(long id, UpdateNoticeRequest updateNoticeRequest) {
        Notice notice = getNoticeById(id);
        notice.updateNoticeTitle(updateNoticeRequest.getTitle());
        notice.updateNoticeContent(updateNoticeRequest.getContent());
    }

    @Transactional
    public void deleteNotice(long id) {
        Notice notice = getNoticeById(id);
        noticeRepository.delete(notice);
    }

    public Page<GetAllNoticesResponse> getAllNotices(Pageable pageable) {
        Page<Notice> noticePage = noticeRepository.findAll(pageable);
        return noticePage.map(EntityToDtoConverter::toAllNoticesResponse);
    }

    @Transactional
    public GetNoticeDetailResponse getNoticeDetail(long id) {
        Notice notice = getNoticeById(id);
        notice.incrementViewCount();
        return EntityToDtoConverter.toDetailNoticeResponse(notice);
    }

    public List<GetRecentNoticesResponse> getRecentNotices() {
        List<Notice> noticeList = noticeRepository.findTop5ByOrderByCreatedAtDesc();
        return noticeList.stream().map(EntityToDtoConverter::toRecentNoticeResponse).collect(Collectors.toList());
    }

}