package team.onepoom.idk.answerReport.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.answerReport.converter.EntityToDtoConverter;
import team.onepoom.idk.common.exception.AnswerReportNotFoundException;
import team.onepoom.idk.common.exception.CompletedReportException;
import team.onepoom.idk.common.exception.QuestionReportNotFoundException;
import team.onepoom.idk.common.exception.ReportReasonNotFoundException;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answerReport.domain.AnswerReport;
import team.onepoom.idk.answerReport.dto.CreateAnswerReportRequest;
import team.onepoom.idk.answerReport.dto.GetAnswerReportResponse;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.questrionReport.domain.QuestionReport;
import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.member.service.MemberService;
import team.onepoom.idk.answerReport.repository.AnswerReportRepository;
import team.onepoom.idk.reportReason.repository.ReportReasonRepository;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerReportService {

    private final AnswerReportRepository answerReportRepository;
    private final MemberService memberService;
    private final ReportReasonRepository reportReasonRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createAnswerReport(CustomUserDetails customUserDetails, CreateAnswerReportRequest request) {
        ReportReason reportReason = reportReasonRepository.findById(request.getReportReasonId()).orElseThrow(ReportReasonNotFoundException::new);
        answerReportRepository.save(new AnswerReport(new Member(customUserDetails.getMember().getId()), new Answer(request.getAnswerId()), reportReason));
    }

    @Transactional
    public void deleteAnswerReport(Long id) {
        AnswerReport answerReport = answerReportRepository.findById(id).orElseThrow(AnswerReportNotFoundException::new);
        answerReport.handle();
    }

    @Transactional
    public void handleAnswerReport(long id) {
        AnswerReport answerReport = getValidatedAnswerReport(id);
        answerReport.getAnswer().blindAnswer();
        memberService.suspendMember(answerReport.getAnswer().getWriter().getId());
        answerReport.handle();
    }

    public Page<GetAnswerReportResponse> getAllAnswerReports(Pageable pageable) {
        Page<AnswerReport> answerReportPage = answerReportRepository.findAllByHandledAtIsNull(pageable);
        return answerReportPage.map(EntityToDtoConverter::toGetAnswerReportResponse);
    }

    private AnswerReport findAnswerReport(long id) {
        return answerReportRepository.findById(id).orElseThrow(() -> new QuestionReportNotFoundException(id));
    }

    //처리 또는 삭제된 신고일 경우 예외처리
    private AnswerReport getValidatedAnswerReport(long id) {
        AnswerReport answerReport = findAnswerReport(id);
        if (answerReport.getHandledAt() != null) {
            throw new CompletedReportException();
        }

        return answerReport;
    }

    @Transactional
    public void setReporterToDeletedMember(Member member) {
        List<AnswerReport> answerReports = answerReportRepository.findAllByReporter(member);
        Member deletedMember = memberRepository.findByEmail("deleted@user.com").orElseThrow(() -> new RuntimeException("탈퇴 사용자가 존재하지 않습니다."));

        for(AnswerReport answerReport : answerReports) {
            answerReport.setReporterToDeletedMember(deletedMember);
        }
    }

}
