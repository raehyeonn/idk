package team.onepoom.idk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.domain.notice.Notice;
import team.onepoom.idk.common.exception.NoticeNotFoundException;
import team.onepoom.idk.domain.notice.dto.DetailNoticeResponse;
import team.onepoom.idk.repository.NoticeRepository;

@Transactional(readOnly = true)
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public void create(Notice notice) {
        noticeRepository.save(notice);
    }

    @Transactional
    public void update(Long id, Notice notice) {
        Notice updateNotice = noticeRepository.findById(id).orElseThrow(
            NoticeNotFoundException::new
        );
        updateNotice.update(notice.getTitle(), notice.getContent());
    }

    @Transactional
    public void delete(long id) {
        Notice deleteNotice = noticeRepository.findById(id).orElseThrow(
            NoticeNotFoundException::new
        );
        noticeRepository.delete(deleteNotice);
    }

    public DetailNoticeResponse showDetailNotice(Long id) {
        return DetailNoticeResponse.from(noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new));
    }

    public Page<Notice> showAllNotice(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}
