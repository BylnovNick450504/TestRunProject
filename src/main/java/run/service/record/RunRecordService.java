package run.service.record;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import run.service.core.CustomSoloRequest;
import run.service.record.dto.RunRecordDTO;

public interface RunRecordService {

    ResponseEntity<?> createRunRecord(HttpServletRequest request, RunRecordDTO recordDTO);
    ResponseEntity<?> deleteRunRecord(HttpServletRequest request, Long id);
    ResponseEntity<?> getRunRecord(HttpServletRequest request, Long id);
    ResponseEntity<?> getAllRunRecords(HttpServletRequest request);
    ResponseEntity<?> updateRunRecord(HttpServletRequest request, RunRecordDTO runRecordDTO);
    ResponseEntity<?> collectStatistic(HttpServletRequest request, CustomSoloRequest startDate);
    ResponseEntity<?> printStatistics(HttpServletRequest request, CustomSoloRequest startDate);
}
