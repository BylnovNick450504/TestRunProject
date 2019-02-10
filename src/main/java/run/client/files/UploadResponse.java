package run.client.files;

public class UploadResponse {
    private String link;

    public UploadResponse() {
    }

    public UploadResponse(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
