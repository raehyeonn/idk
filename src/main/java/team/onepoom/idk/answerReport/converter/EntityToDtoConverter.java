package team.onepoom.idk.answerReport.converter;

import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answerReport.domain.AnswerReport;
import team.onepoom.idk.answerReport.dto.GetAnswerReportResponse;

public class EntityToDtoConverter {

    public static GetAnswerReportResponse toGetAnswerReportResponse(AnswerReport answerReport) {
        Answer answer = answerReport.getAnswer();

        return GetAnswerReportResponse.builder()
        .answerReportId(answerReport.getId())
        .createdAt(answerReport.getCreatedAt())
        .reporter(answerReport.getReporter().getNickname())
        .reportReason(answerReport.getReportReason())
        .writer(answer.getWriter().getNickname())
        .content(answer.getContent())
        .build();
    }

}
