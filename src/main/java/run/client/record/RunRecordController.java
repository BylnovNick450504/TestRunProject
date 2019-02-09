package run.client.record;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.service.core.CustomSoloRequest;
import run.service.record.RunRecordService;
import run.service.record.dto.RunRecordDTO;

@RestController
public class RunRecordController {

    private RunRecordService runRecordService;

    @Autowired
    public RunRecordController(RunRecordService runRecordService) {
        this.runRecordService = runRecordService;
    }

    @RequestMapping(value = "/run/record/create", method = RequestMethod.POST)
    public ResponseEntity<?> createRunRecord(HttpServletRequest request, @RequestBody RunRecordDTO recordDTO) {
        return runRecordService.createRunRecord(request, recordDTO);
    }

    @RequestMapping(value = "run/record/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRunRecord(HttpServletRequest request, @PathVariable Long id) {
        return runRecordService.deleteRunRecord(request, id);
    }

    @RequestMapping(value = "run/record/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRunRecord(HttpServletRequest request, @RequestBody RunRecordDTO runRecordDTO) {
        return runRecordService.updateRunRecord(request, runRecordDTO);
    }

    @RequestMapping(value = "run/record/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRunRecord(HttpServletRequest request, @PathVariable Long id) {
        return runRecordService.getRunRecord(request, id);
    }

    @RequestMapping(value = "run/record/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRunRecords(HttpServletRequest request) {
        return runRecordService.getAllRunRecords(request);
    }

    @RequestMapping(value = "run/record/report", method = RequestMethod.POST)
    public ResponseEntity<?> getReport(HttpServletRequest request, @RequestBody CustomSoloRequest dateFrom) {
        return runRecordService.collectStatistic(request, dateFrom);
    }

    @RequestMapping(value = "run/record/report/print", method = RequestMethod.POST)
    public ResponseEntity<?> getPrintReport(HttpServletRequest request, @RequestBody CustomSoloRequest dateFrom) {
        return runRecordService.printStatistics(request, dateFrom);
    }
}
