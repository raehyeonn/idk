package team.onepoom.idk.questrionReport.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.answerReport.domain.AnswerReport;
import team.onepoom.idk.common.exception.CompletedReportException;
import team.onepoom.idk.common.exception.QuestionReportNotFoundException;
import team.onepoom.idk.common.exception.ReportReasonNotFoundException;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.questrionReport.converter.EntityToDtoConverter;
import team.onepoom.idk.questrionReport.domain.QuestionReport;
import team.onepoom.idk.questrionReport.dto.CreateQuestionReportRequest;
import team.onepoom.idk.questrionReport.dto.GetQuestionReportResponse;
import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.service.MemberService;
import team.onepoom.idk.question.service.QuestionService;
import team.onepoom.idk.questrionReport.repository.QuestionReportRepository;
import team.onepoom.idk.reportReason.repository.ReportReasonRepository;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class QuestionReportService {

    private final QuestionReportRepository questionReportRepository;
    private final QuestionService questionService;
    private final MemberService memberService;
    private final ReportReasonRepository reportReasonRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createQuestionReport(CustomUserDetails customUserDetails, CreateQuestionReportRequest createQuestionReportRequest) {
        // 유효성 검사
        questionService.getQuestionById(createQuestionReportRequest.getQuestionId());
        ReportReason reportReason = reportReasonRepository.findById(createQuestionReportRequest.getReportReasonId()).orElseThrow(ReportReasonNotFoundException::new);

        // QuestionReport 생성 후 저장
        QuestionReport questionReport = new QuestionReport(new Member(customUserDetails.getId()), new Question(createQuestionReportRequest.getQuestionId()), reportReason);
        questionReportRepository.save(questionReport);
    }

    private QuestionReport getQuestionReportById(long id) {
        return questionReportRepository.findById(id).orElseThrow(() -> new QuestionReportNotFoundException(id));
    }

    //처리 또는 삭제된 신고일 경우 예외처리
    private QuestionReport getVerifiedQuestionReport(long id) {
        QuestionReport questionReport = getQuestionReportById(id);

        if (questionReport.getHandledAt() != null) {
            throw new CompletedReportException();
        }

        return questionReport;
    }

    @Transactional
    public void deleteQuestionReport(long id) {
        QuestionReport questionReport = getVerifiedQuestionReport(id);
        questionReport.handle();
    }

    @Transactional(readOnly = true)
    public Page<GetQuestionReportResponse> getAllQuestionReports(Pageable pageable) {
        return questionReportRepository.findAllByHandledAtIsNull(pageable).map(EntityToDtoConverter::toQuestionReportResponse);
    }

    @Transactional
    public void handleQuestionReport(long id) {
        QuestionReport questionReport = getVerifiedQuestionReport(id);
        questionReport.getQuestion().blindQuestion();
        memberService.suspendMember(questionReport.getQuestion().getWriter().getId());
        questionReport.handle();
    }

    @Transactional
    public void setReporterToDeletedMember(Member member) {
        List<QuestionReport> questionReports = questionReportRepository.findAllByReporter(member);
        Member deletedMember = memberRepository.findByEmail("deleted@user.com").orElseThrow(() -> new RuntimeException("탈퇴 사용자가 존재하지 않습니다."));

        for(QuestionReport questionReport : questionReports) {
            questionReport.setReporterToDeletedMember(deletedMember);
        }
    }

}
