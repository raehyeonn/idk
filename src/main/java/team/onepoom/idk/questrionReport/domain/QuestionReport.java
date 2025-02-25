package team.onepoom.idk.questrionReport.domain;

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
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_reports")
public class QuestionReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    private String reportReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private ZonedDateTime handledAt;

    @Builder
    public QuestionReport(Member reporter, Question question, ReportReason reportReason) {
        this.reporter = reporter;
        this.question = question;
        this.reportReason = reportReason.getContent();
    }

    public void handle(){
        this.handledAt = ZonedDateTime.now();
    }

    public void setReporterToDeletedMember(Member member) {
        this.reporter = member;
    }

}