package team.onepoom.idk.reportReason.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.ReportReasonNotFoundException;
import team.onepoom.idk.reportReason.converter.DtoToEntityConverter;
import team.onepoom.idk.reportReason.converter.EntityToDtoConverter;
import team.onepoom.idk.reportReason.domain.ReportReason;
import team.onepoom.idk.reportReason.dto.AllReportReasonsResponse;
import team.onepoom.idk.reportReason.dto.CreateReportReasonRequest;
import team.onepoom.idk.reportReason.repository.ReportReasonRepository;

@Service
@RequiredArgsConstructor
public class ReportReasonService {

    private final ReportReasonRepository reportReasonRepository;

    @Transactional
    public void createReportReason(CreateReportReasonRequest createReportReasonRequest) {
        ReportReason reportReason = DtoToEntityConverter.toEntity(createReportReasonRequest);
        reportReasonRepository.save(reportReason);
    }

    @Transactional
    public void deleteReportReason(Long id) {
        ReportReason reportReason = reportReasonRepository.findById(id).orElseThrow(ReportReasonNotFoundException::new);
        reportReasonRepository.delete(reportReason);
    }

    public List<AllReportReasonsResponse> getAllReportReasons() {
        List<ReportReason> reportReasons = reportReasonRepository.findAll();
        return reportReasons.stream().map(EntityToDtoConverter::toAllReportReasonResponse).collect(Collectors.toList());
    }

}
