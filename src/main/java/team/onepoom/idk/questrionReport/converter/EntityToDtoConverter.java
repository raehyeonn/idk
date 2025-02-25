package team.onepoom.idk.questrionReport.converter;

import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.questrionReport.domain.QuestionReport;
import team.onepoom.idk.questrionReport.dto.GetQuestionReportResponse;

public class EntityToDtoConverter {

    public static GetQuestionReportResponse toQuestionReportResponse(QuestionReport questionReport) {
        Question question = questionReport.getQuestion();

        return GetQuestionReportResponse.builder()
        .questionReportId(questionReport.getId())
        .questionId(question.getId())
        .writer(question.getWriter().getNickname())
        .title(question.getTitle())
        .content(question.getContent())
        .reporter(questionReport.getReporter().getNickname())
        .reportReason(questionReport.getReportReason())
        .createdAt(questionReport.getCreatedAt())
        .build();
    }

}
