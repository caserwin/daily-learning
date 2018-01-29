package generics;

import java.io.IOException;

/**
 * Created by yidxue on 2018/1/29
 */
public interface BaseFormat<IT> {

    /**
     *
     */
    IT writeRecord(IT record) throws IOException;

    /**
     *
     */
    void writeRecord(IT record, IT name) throws IOException;

}
