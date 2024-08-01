package team.onepoom.idk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.domain.answerReport.AnswerReport;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {
    Page<AnswerReport> findAllByCompletedAtIsNull(Pageable pageable);
}
