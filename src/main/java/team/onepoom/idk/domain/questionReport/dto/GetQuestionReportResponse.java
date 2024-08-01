package team.onepoom.idk.domain.questionReport.dto;

import java.time.ZonedDateTime;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.question.Question;
import team.onepoom.idk.domain.questionReport.QuestionReport;

public record GetQuestionReportResponse(
    long questionId,
    String title,
    String content,
    //writerDto로 변경 가능 (작성자)
    Provider writer,
    //신고 날짜
    ZonedDateTime createdAt,
    String reportReason
) {

    public static GetQuestionReportResponse from(QuestionReport questionReport) {
        Question question = questionReport.getQuestion();
        return new GetQuestionReportResponse(
            question.getId(), question.getTitle(), question.getContent(),
            question.getWriter().toProvider(),
            questionReport.getCreatedAt(), questionReport.getReportReason().getContent()
        );
    }
}
