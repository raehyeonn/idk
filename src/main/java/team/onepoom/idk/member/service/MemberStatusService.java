package team.onepoom.idk.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.answer.service.AnswerService;
import team.onepoom.idk.answerReport.service.AnswerReportService;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.question.service.QuestionService;
import team.onepoom.idk.questrionReport.service.QuestionReportService;

@Service
@RequiredArgsConstructor
public class MemberStatusService {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final QuestionReportService questionReportService;
    private final AnswerReportService answerReportService;
    private final MemberRepository memberRepository;


    private Member getAuthenticatedMember(UserDetails userDetails) {
        // UserDetails에서 이메일을 추출하여 Member 엔티티를 조회
        String email = userDetails.getUsername();  // UserDetails에서 username은 이메일일 경우 많음
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("인증된 사용자를 찾을 수 없습니다."));
    }


    @Transactional
    public void setWriterToDeletedMember(Member member) {
        questionService.setWriterToDeletedMember(member);
        answerService.setWriterToDeletedMember(member);
        questionReportService.setReporterToDeletedMember(member);
        answerReportService.setReporterToDeletedMember(member);
    }



    @Transactional
    public void deleteMember(UserDetails userDetails) {
        Member member = getAuthenticatedMember(userDetails);
        setWriterToDeletedMember(member);
        memberRepository.delete(member);
    }
}
