package team.onepoom.idk.questrionReport.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.questrionReport.domain.QuestionReport;

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long> {
    Page<QuestionReport> findAllByHandledAtIsNull(Pageable pageable);
    List<QuestionReport> findAllByReporter(Member member);
}
