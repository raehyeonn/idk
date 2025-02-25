package team.onepoom.idk.notice.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.notice.dto.CreateNoticeResponse;
import team.onepoom.idk.notice.dto.GetAllNoticesResponse;
import team.onepoom.idk.notice.dto.CreateNoticeRequest;
import team.onepoom.idk.notice.dto.GetNoticeDetailResponse;
import team.onepoom.idk.notice.dto.GetRecentNoticesResponse;
import team.onepoom.idk.notice.dto.UpdateNoticeRequest;
import team.onepoom.idk.notice.service.NoticeService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public CreateNoticeResponse create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody CreateNoticeRequest createNoticeRequest) {
        return noticeService.createNotice(customUserDetails, createNoticeRequest);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody UpdateNoticeRequest updateNoticeRequest) {
        noticeService.updateNotice(id, updateNoticeRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        noticeService.deleteNotice(id);
    }

    @GetMapping
    public Page<GetAllNoticesResponse> getAll(Pageable pageable) {
        return noticeService.getAllNotices(pageable);
    }

    @GetMapping("/{id}")
    public GetNoticeDetailResponse getDetail(@PathVariable long id) {
        return noticeService.getNoticeDetail(id);
    }

    @GetMapping("/recent")
    public List<GetRecentNoticesResponse> getRecent() {
        return noticeService.getRecentNotices();
    }

}
