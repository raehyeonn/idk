package team.onepoom.idk.answerReport.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answerReport.domain.AnswerReport;
import team.onepoom.idk.member.domain.Member;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {
    Page<AnswerReport> findAllByHandledAtIsNull(Pageable pageable);
    List<AnswerReport> findAllByReporter(Member member);
}