package team.onepoom.idk.controller;

import jakarta.annotation.security.RolesAllowed;
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
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.questionReport.dto.CreateQuestionReportRequest;
import team.onepoom.idk.domain.questionReport.dto.GetQuestionReportResponse;
import team.onepoom.idk.service.QuestionReportService;

@RestController
@RequestMapping("/api/questions/reports")
@RequiredArgsConstructor
public class QuestionReportController {

    private final QuestionReportService questionReportService;

    @RolesAllowed({"USER"})
    @PostMapping
    public void reportQuestion(@AuthenticationPrincipal Provider provider,
        @RequestBody CreateQuestionReportRequest request) {

        questionReportService.create(provider, request);
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public Page<GetQuestionReportResponse> getAllQuestionReports(
        @AuthenticationPrincipal Provider provider, Pageable pageable) {
        return questionReportService.getQuestionsReports(provider, pageable);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("handle/{id}")
    public void handleQuestionReport(@PathVariable("id") long id) {
        questionReportService.handleQuestionReport(id);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("{id}")
    public void deleteQuestionReport(@PathVariable("id") long id) {
        questionReportService.deleteQuestionReport(id);
    }


}
