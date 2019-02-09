package run.service.record;

import java.util.Collections;
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
import run.service.record.dto.RunRecordDTO;
import run.service.user.UserService;
import run.service.util.Converter;

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
    public ResponseEntity<?> createReport(HttpServletRequest request, CustomSoloRequest startDate) {
        return null;
    }
}
