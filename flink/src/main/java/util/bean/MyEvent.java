package util.bean;

/**
 * Created by yidxue on 2018/8/19
 *
 * @author yidxue
 */
public class MyEvent {
    public long timestamp;
    public int value;
    public String message;

    public MyEvent(int value, String message, long timestamp) {
        this.timestamp = timestamp;
        this.value = value;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getMessage() + "\t" + this.getValue() + "\t" + this.getTimestamp();
    }
}
