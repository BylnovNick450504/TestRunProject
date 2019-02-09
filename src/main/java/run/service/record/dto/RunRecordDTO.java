package run.service.record.dto;

public class RunRecordDTO {

    private Long id;
    private Long userId;
    private double distance;
    private double duration;
    private String runDateStr;

    public RunRecordDTO() {
    }

    public RunRecordDTO(Long id, Long userId, double distance, double duration, String runDateStr) {
        this.id = id;
        this.userId = userId;
        this.distance = distance;
        this.duration = duration;
        this.runDateStr = runDateStr;
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

    public String getRunDateStr() {
        return runDateStr;
    }

    public void setRunDateStr(String runDateStr) {
        this.runDateStr = runDateStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
