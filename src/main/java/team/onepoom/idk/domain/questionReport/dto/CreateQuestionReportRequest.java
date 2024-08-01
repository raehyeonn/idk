package team.onepoom.idk.domain.questionReport.dto;

import jakarta.validation.constraints.Positive;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.question.Question;
import team.onepoom.idk.domain.questionReport.QuestionReport;
import team.onepoom.idk.domain.reportReason.ReportReason;
import team.onepoom.idk.domain.user.User;

public record CreateQuestionReportRequest(@Positive long questionId,
                                          @Positive long reportReasonId) {

    public QuestionReport toEntity(Provider provider) {
        return new QuestionReport(new User(provider.id()), new ReportReason(reportReasonId),
            new Question(questionId));
    }
}
