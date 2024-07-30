package team.onepoom.idk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.domain.notice.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
