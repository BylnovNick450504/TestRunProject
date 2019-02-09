package run.service.core;

public class CustomSoloRequest {
    private Long id;

    public CustomSoloRequest() {
    }

    public CustomSoloRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
