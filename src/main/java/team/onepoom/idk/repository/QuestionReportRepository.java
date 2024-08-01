package team.onepoom.idk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.domain.questionReport.QuestionReport;

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long> {
    Page<QuestionReport> findAllByCompletedAtIsNull(Pageable pageable);
}
