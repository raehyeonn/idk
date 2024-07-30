package team.onepoom.idk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.domain.notice.Notice;
import team.onepoom.idk.domain.notice.NoticeNotFoundException;
import team.onepoom.idk.repository.NoticeRepository;

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

    @Transactional
    public Notice showDetailNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(NoticeNotFoundException::new);
    }

    @Transactional
    public Page<Notice> showAllNotice(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}
