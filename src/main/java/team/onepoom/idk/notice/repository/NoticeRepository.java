package team.onepoom.idk.notice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findTop5ByOrderByCreatedAtDesc();

}
