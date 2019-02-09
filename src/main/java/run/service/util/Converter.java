package run.service.util;

import java.util.function.Function;
import run.persistence.user.RunRecord;
import run.persistence.user.User;
import run.service.record.dto.RunRecordDTO;

public final class Converter {

    private Converter() {}

    public static Function<RunRecordDTO, RunRecord> toRecord(User creator) {
        return recordDTO -> {
            RunRecord record = new RunRecord();
            if (recordDTO.getId() != null) {
                record.setId(recordDTO.getId());
            }
            record.setDistance(recordDTO.getDistance());
            record.setDuration(recordDTO.getDuration());
            record.setRunDate(DateHelper.stringToDate(recordDTO.getRunDateStr()));
            record.setUser(creator);
            return record;
        };
    }

    public static Function<RunRecord, RunRecordDTO> toRecordDTO() {
        return runRecord -> {
          RunRecordDTO runRecordDTO = new RunRecordDTO();
            runRecordDTO.setId(runRecord.getId());
            runRecordDTO.setUserId(runRecord.getUser().getId());
            runRecordDTO.setDistance(DoubleUtils.roundDouble(runRecord.getDistance(), 3));
            runRecordDTO.setDuration(DoubleUtils.roundDouble(runRecord.getDuration(), 3));
            runRecordDTO.setRunDateStr(DateHelper.dateToString(runRecord.getRunDate()));
            return runRecordDTO;
        };
    }

}
