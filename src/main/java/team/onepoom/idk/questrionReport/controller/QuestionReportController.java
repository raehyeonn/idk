package team.onepoom.idk.questrionReport.controller;

import jakarta.validation.Valid;
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
import team.onepoom.idk.questrionReport.dto.CreateQuestionReportRequest;
import team.onepoom.idk.questrionReport.dto.GetQuestionReportResponse;
import team.onepoom.idk.questrionReport.service.QuestionReportService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports/questions")
public class QuestionReportController {

    private final QuestionReportService questionReportService;

    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody CreateQuestionReportRequest createQuestionReportRequest) {
        questionReportService.createQuestionReport(customUserDetails, createQuestionReportRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        questionReportService.deleteQuestionReport(id);
    }

    @GetMapping
    public Page<GetQuestionReportResponse> getAll(Pageable pageable) {
        return questionReportService.getAllQuestionReports(pageable);
    }

    @PostMapping("/handle/{id}")
    public void handle(@PathVariable("id") long id) {
        questionReportService.handleQuestionReport(id);
    }

}
