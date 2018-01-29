package generics;

import java.io.IOException;

/**
 * Created by yidxue on 2018/1/29
 */
public class Format implements BaseFormat<String> {

    @Override
    public String writeRecord(String record) throws IOException {
        return record;
    }

    @Override
    public void writeRecord(String record, String name) throws IOException {

    }
}
