package util.bean;

import lombok.Data;

/**
 * Created by yidxue on 2018/8/19
 *
 * @author yidxue
 */
@Data
public class MyEvent {
    public long timestamp;
    public int value;
    public String message;

    public MyEvent(int value, String message, long timestamp) {
        this.timestamp = timestamp;
        this.value = value;
        this.message = message;
    }
}
