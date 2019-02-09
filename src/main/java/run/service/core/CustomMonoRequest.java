package run.service.core;

import java.util.List;

public class CustomMonoRequest {
    private List<Long> ids;

    public CustomMonoRequest() {}

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
