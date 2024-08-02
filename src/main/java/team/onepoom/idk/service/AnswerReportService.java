package team.onepoom.idk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.AnswerReportNotFoundException;
import team.onepoom.idk.domain.answerReport.AnswerReport;
import team.onepoom.idk.domain.answerReport.dto.GetAnswerReportResponse;
import team.onepoom.idk.repository.AnswerReportRepository;

@Service
@Transactional(readOnly = true)
public class AnswerReportService {
    private final AnswerReportRepository answerReportRepository;
    private final UserService userService;

    @Autowired
    public AnswerReportService(AnswerReportRepository answerReportRepository, UserService userService) {
        this.answerReportRepository = answerReportRepository;
        this.userService = userService;
    }

    @Transactional
    public void create(AnswerReport answerReport) {
        answerReportRepository.save(answerReport);
    }

    @Transactional
    public void delete(Long id) {
        AnswerReport deleteAnswerReport = answerReportRepository.findById(id).orElseThrow(AnswerReportNotFoundException::new);
        deleteAnswerReport.completed();
    }

    @Transactional
    public void suspension(long id) {
        AnswerReport answerReport = answerReportRepository.findById(id).orElseThrow(AnswerReportNotFoundException::new);
        answerReport.getAnswer().reported();
        userService.suspend(answerReport.getAnswer().getWriter().getId());
        answerReport.completed();
    }

    public Page<GetAnswerReportResponse> getAnswerReport(Pageable pageable) {
        Page<AnswerReport> answerReportPage = answerReportRepository.findAllByCompletedAtIsNull(pageable);
        return answerReportPage.map(GetAnswerReportResponse::from);
    }
}