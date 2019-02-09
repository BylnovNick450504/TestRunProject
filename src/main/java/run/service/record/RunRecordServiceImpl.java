package run.service.record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import run.persistence.user.RunRecord;
import run.persistence.user.User;
import run.service.core.CustomSoloRequest;
import run.service.core.ResponseStatus;
import run.service.record.dto.ReportDTO;
import run.service.record.dto.RunRecordDTO;
import run.service.user.UserService;
import run.service.util.Constants;
import run.service.util.Converter;
import run.service.util.DateHelper;

@Service
public class RunRecordServiceImpl implements RunRecordService {

    private UserService userService;
    private RunRecordRepository runRecordRepository;

    @Autowired
    public RunRecordServiceImpl(UserService userService,
                                RunRecordRepository runRecordRepository
    ) {
        this.userService = userService;
        this.runRecordRepository = runRecordRepository;
    }

    @Override
    public ResponseEntity<?> createRunRecord(HttpServletRequest request, RunRecordDTO recordDTO) {
        if (recordDTO == null) {
            return ResponseEntity.ok(new ResponseStatus(false, "request body is missing"));
        }
        User owner = userService.getUserByToken(request);
        RunRecord record = Converter.toRecord(owner).apply(recordDTO);
        record = runRecordRepository.save(record);
        if (record == null) {
            return ResponseEntity.ok(new ResponseStatus(false, "couldn't save entity"));
        }
        return new ResponseEntity<>(Converter.toRecordDTO().apply(record), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteRunRecord(HttpServletRequest request, Long id) {
        if (id == null) {
            return new ResponseEntity<>(new ResponseStatus(false, "bad request: no input value"),
                    HttpStatus.OK);
        }
        User owner = userService.getUserByToken(request);
        RunRecord record = runRecordRepository.findOne(id);
        if (record == null) {
            return new ResponseEntity<>(new ResponseStatus(false, String.format("no record with id = %d", id)),
                    HttpStatus.OK);
        }
        if (!owner.equals(record.getUser())) {
            return new ResponseEntity<>(new ResponseStatus(false, "security violation"),
                    HttpStatus.OK);
        }
        runRecordRepository.delete(id);
        return ResponseEntity.ok(new ResponseStatus(true, String.format("record with id = %d was deleted", id)));
    }

    @Override
    public ResponseEntity<?> getRunRecord(HttpServletRequest request, Long id) {
        if (id == null) {
            return new ResponseEntity<>(new ResponseStatus(false, "bad request: no input value"),
                    HttpStatus.OK);
        }
        RunRecord record = runRecordRepository.findByIdAndUser(id, userService.getUserByToken(request));
        if (record == null) {
            return new ResponseEntity<>(new ResponseStatus(false, String.format("no record with id = %d", id)),
                    HttpStatus.OK);
        }
        return ResponseEntity.ok(Converter.toRecordDTO().apply(record));
    }

    @Override
    public ResponseEntity<?> getAllRunRecords(HttpServletRequest request) {
        User owner = userService.getUserByToken(request);
        List<RunRecordDTO> result;
        List<RunRecord> runRecords = runRecordRepository.findAllByUser(owner);
        if (runRecords == null || runRecords.isEmpty()) {
            result = Collections.emptyList();
        } else {
            result = runRecords.stream().map(Converter.toRecordDTO()).collect(Collectors.toList());
        }
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<?> updateRunRecord(HttpServletRequest request, RunRecordDTO recordDTO) {
        if (recordDTO == null || recordDTO.getId() == null) {
            return ResponseEntity.ok(new ResponseStatus(false, "bad request: no input value"));
        }
        User owner = userService.getUserByToken(request);
        RunRecord oldRecord = runRecordRepository.findByIdAndUser(recordDTO.getId(), owner);
        if (oldRecord == null) {
            return new ResponseEntity<>(new ResponseStatus(false, "security violation"),
                    HttpStatus.OK);
        }
        RunRecord updatedRecord = runRecordRepository.save(Converter.toRecord(owner).apply(recordDTO));
        return ResponseEntity.ok(Converter.toRecordDTO().apply(updatedRecord));
    }

    @Override
    public ResponseEntity<?> collectStatistic(HttpServletRequest request, CustomSoloRequest startDate) {
        Date from = DateHelper.stringToDate(startDate.getDateFrom());
        Date upBounder = DateHelper.stringToDate(startDate.getDateTo());
        if (from.after(upBounder)) {
            return ResponseEntity.ok(new ResponseStatus(false, "bad request: wrong input"));
        }
        User owner = userService.getUserByToken(request);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        int weekNumber = 1;
        List<ReportDTO> reports = new ArrayList<>();
        while (true) {
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            Date to = calendar.getTime();
            if (upBounder.before(to)) {
                break;
            }
            List<RunRecord> runRecords = runRecordRepository.findAllByRunDateBetweenAndUser(from, to, owner);

            ReportDTO reportItem = createReportItem(
                    createReportName(weekNumber++, to, from),
                    runRecords);
            reports.add(reportItem);

            Calendar shift = Calendar.getInstance();
            shift.setTime(to);
            shift.add(Calendar.DATE, 1);
            from = shift.getTime();
        }
        return ResponseEntity.ok(reports);
    }

    private String createReportName(int weekNumber, Date to, Date from) {
        return String.format("Week %d: (%s / %s)", weekNumber,
                DateHelper.dateToString(from, Constants.DATE_FORMAT),
                DateHelper.dateToString(to, Constants.DATE_FORMAT));
    }

    private ReportDTO createReportItem(String name, List<RunRecord> runRecords) {
        double totalDistance = 0.0;
        double averageSpeed = 0.0;
        double averageTime = 0.0;
        if (runRecords != null && !runRecords.isEmpty()) {
            for (RunRecord r : runRecords) {
                totalDistance += r.getDistance();
                averageTime += r.getDuration();
                averageSpeed += r.getDistance() / r.getDuration();
            }
            averageTime /= runRecords.size();
        }
        return new ReportDTO(name, averageSpeed, averageTime, totalDistance);
    }

    @Override
    public ResponseEntity<?> printStatistics(HttpServletRequest request, CustomSoloRequest startDate) {
        ResponseEntity<?> responseEntity = collectStatistic(request, startDate);
        List<ReportDTO> reports = (List<ReportDTO>) responseEntity.getBody();
        if (reports == null || reports.isEmpty()) {
            return ResponseEntity.ok(new ResponseStatus(false, "bad request: no statistic for that time"));
        }
        String result = reports.stream().map(ReportDTO::toString).collect(Collectors.joining());
        return ResponseEntity.ok(result);
    }
}
