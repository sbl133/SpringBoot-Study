package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private String createId() {
        // UUID 앞 8자리만 사용
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String createNextId() {
        return new TraceId(id, level + 1);
    }
}
