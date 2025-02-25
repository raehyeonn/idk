package team.onepoom.idk.answerReport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.answerReport.dto.CreateAnswerReportRequest;
import team.onepoom.idk.answerReport.dto.GetAnswerReportResponse;
import team.onepoom.idk.answerReport.service.AnswerReportService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequestMapping("/api/reports/answers")
@RequiredArgsConstructor
public class AnswerReportController {

    private final AnswerReportService answerReportService;

    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CreateAnswerReportRequest createAnswerReportRequest) {
        answerReportService.createAnswerReport(customUserDetails, createAnswerReportRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        answerReportService.deleteAnswerReport(id);
    }

    @GetMapping
    public Page<GetAnswerReportResponse> getAll(Pageable pageable) {
        return answerReportService.getAllAnswerReports(pageable);
    }

    @PostMapping("/handle/{id}")
    public void handle(@PathVariable Long id) {
        answerReportService.handleAnswerReport(id);
    }

}
