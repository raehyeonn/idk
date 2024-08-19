package team.onepoom.idk.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answerReport.dto.CreateAnswerReportRequest;
import team.onepoom.idk.domain.answerReport.dto.GetAnswerReportResponse;
import team.onepoom.idk.service.AnswerReportService;

@RestController
@RequestMapping("/api/reports/answers")
public class AnswerReportController {
    private final AnswerReportService answerReportService;

    @Autowired
    public AnswerReportController(AnswerReportService answerReportService) {
        this.answerReportService = answerReportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"USER"})
    public void createAnswerReport(@AuthenticationPrincipal Provider provider, @RequestBody CreateAnswerReportRequest request) {
        answerReportService.create(provider, request);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN"})
    public void deleteAnswerReport(@PathVariable Long id) {
        answerReportService.delete(id);
    }

    @GetMapping
    @RolesAllowed({"ADMIN"})
    public Page<GetAnswerReportResponse> getAllAnswerReports(Pageable pageable) {
        return answerReportService.getAnswerReport(pageable);
    }

    @PostMapping("/{id}")
    @RolesAllowed({"ADMIN"})
    public void suspensionReportedUser(@PathVariable Long id) {
        answerReportService.suspension(id);
    }
}
