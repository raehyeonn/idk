package team.onepoom.idk.reportReason.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.reportReason.dto.AllReportReasonsResponse;
import team.onepoom.idk.reportReason.dto.CreateReportReasonRequest;
import team.onepoom.idk.reportReason.service.ReportReasonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports/reasons")
public class ReportReasonController {

    private final ReportReasonService reportReasonService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateReportReasonRequest createReportReasonRequest) {
        reportReasonService.createReportReason(createReportReasonRequest);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        reportReasonService.deleteReportReason(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AllReportReasonsResponse>> getAll() {
        List<AllReportReasonsResponse> reportReasons = reportReasonService.getAllReportReasons();
        return ResponseEntity.ok(reportReasons);
    }

}
