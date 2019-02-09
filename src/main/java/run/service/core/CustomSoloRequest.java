package run.service.core;

public class CustomSoloRequest {
    private Long id;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
