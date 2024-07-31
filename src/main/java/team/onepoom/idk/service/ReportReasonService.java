package team.onepoom.idk.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.ReportReasonNotFoundException;
import team.onepoom.idk.domain.reportReason.ReportReason;
import team.onepoom.idk.domain.reportReason.dto.AllReportReasonResponse;
import team.onepoom.idk.repository.ReportReasonRepository;

@Service
@Transactional(readOnly = true)
public class ReportReasonService {
    private final ReportReasonRepository reportReasonRepository;

    @Autowired
    public ReportReasonService(ReportReasonRepository reportReasonRepository) {
        this.reportReasonRepository = reportReasonRepository;
    }

    @Transactional
    public void create(ReportReason reportReason) {
        reportReasonRepository.save(reportReason);
    }

    @Transactional
    public void delete(Long id) {
        ReportReason deleteReportReason = reportReasonRepository.findById(id).orElseThrow(ReportReasonNotFoundException::new);
        reportReasonRepository.delete(deleteReportReason);
    }

    public List<AllReportReasonResponse> findAllReportReasons() {
        List<ReportReason> reportReasons = reportReasonRepository.findAll();
        return reportReasons.stream().map(AllReportReasonResponse::from).collect(Collectors.toList());
    }
}
