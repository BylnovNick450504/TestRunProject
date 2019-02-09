package run.service.record.dto;

public class ReportDTO {

    private String name;
    private double averageSpeed;
    private double averageTime;
    private double totalDistance;

    public ReportDTO(String name, double averageSpeed, double averageTime, double totalDistance) {
        this.name = name;
        this.averageSpeed = averageSpeed;
        this.averageTime = averageTime;
        this.totalDistance = totalDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                "Av. Speed: %f      Av. Time: %f \n"+
                "Total Distance: %f \n",
                getName(),
                getAverageSpeed(),
                getAverageTime(),
                getTotalDistance());
    }
}
