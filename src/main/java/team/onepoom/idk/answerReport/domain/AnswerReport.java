package team.onepoom.idk.answerReport.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.common.BaseEntity;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.member.domain.Member;

@Entity
@Table(name = "answer_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private String reportReason;
    private ZonedDateTime handledAt;

    @Builder
    public AnswerReport(Member reporter, Answer answer, ReportReason reportReason) {
        this.reporter = reporter;
        this.answer = answer;
        this.reportReason = reportReason.getContent();
    }

    public void handle() {
        this.handledAt = ZonedDateTime.now();
    }

    public void setReporterToDeletedMember(Member member) {
        this.reporter = member;
    }

}
