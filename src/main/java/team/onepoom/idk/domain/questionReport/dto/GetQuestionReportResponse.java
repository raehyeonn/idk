package team.onepoom.idk.domain.questionReport.dto;

import java.time.ZonedDateTime;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.question.Question;
import team.onepoom.idk.domain.questionReport.QuestionReport;
import team.onepoom.idk.domain.user.dto.WriterDTO;

public record GetQuestionReportResponse(
    long questionReportId,
    long questionId,
    String title,
    String reporter,
    String content,
    WriterDTO writer,
    //신고 날짜
    ZonedDateTime createdAt,
    String reportReason
) {

    public static GetQuestionReportResponse from(QuestionReport questionReport) {
        Question question = questionReport.getQuestion();
        return new GetQuestionReportResponse(
            questionReport.getId(), question.getId(), question.getTitle(),
            questionReport.getReporter().getNickname(),
            question.getContent(),
            new WriterDTO(question.getWriter()),
            questionReport.getCreatedAt(), questionReport.getReportReason()
        );
    }
}
