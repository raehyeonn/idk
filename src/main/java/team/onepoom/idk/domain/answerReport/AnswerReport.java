package team.onepoom.idk.domain.answerReport;

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
import team.onepoom.idk.common.exception.CompletedReportException;
import team.onepoom.idk.domain.BaseEntity;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.reportReason.ReportReason;
import team.onepoom.idk.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer_reports")
public class AnswerReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    private String reportReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private ZonedDateTime completedAt; // 처리 일자

    //생성 메서드
    @Builder
    public AnswerReport(Provider provider, ReportReason reportReason, Answer answer) {
        this.reporter = new User(provider.id());
        this.reportReason = reportReason.getContent();
        this.answer = answer;
    }

    public void completed() {
        if(this.completedAt != null) {
            throw new CompletedReportException();
        }
        this.completedAt = ZonedDateTime.now();
    }
}
