package run.service.core;

public class ResponseStatus {
    private boolean success = false;
    private String status = "bad response";

    public ResponseStatus() {

    }

    public ResponseStatus(boolean success) {
        this.success = success;
        this.status = "good response";
    }

    public ResponseStatus(boolean success, String status) {
        this.success = success;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
