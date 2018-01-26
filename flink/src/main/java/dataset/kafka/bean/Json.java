package dataset.kafka.bean;

import lombok.Data;

/**
 * @author yidxue
 */
@Data
public class Json {
    private long timeStamp;
    private int id;
    private String name;

    public Json(long timeStamp, int id, String name) {
        this.timeStamp = timeStamp;
        this.id = id;
        this.name = name;
    }
}
