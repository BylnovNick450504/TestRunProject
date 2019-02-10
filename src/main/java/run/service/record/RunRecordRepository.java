package run.service.record;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import run.persistence.user.RunRecord;
import run.persistence.user.User;

public interface RunRecordRepository extends PagingAndSortingRepository<RunRecord, Long> {
    List<RunRecord> findAllByUser(User user);
    RunRecord findByIdAndUser(Long id, User user);
    List<RunRecord> findAllByUserOrderByRunDateAsc(User user);
}
