package run.persistence.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import run.persistence.core.BaseEntity;
import run.service.util.DateHelper;

@Entity
@Table(name = "run_record")
public class RunRecord extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double distance;
    private double duration;

    @NotNull
    private Date runDate;

    public RunRecord() {
    }

    public RunRecord(User user, double distance, double duration, String runDateStr) {
        this.user = user;
        this.distance = distance;
        this.duration = duration;
        this.runDate = DateHelper.stringToDate(runDateStr);
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }
}
