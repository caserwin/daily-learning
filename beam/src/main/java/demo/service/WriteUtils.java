/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: WriteUtils
 * Author:   lixchen
 * Date:     2018/8/16 2:44 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package demo.service;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.parquet.ParquetIO;
import org.apache.beam.sdk.values.PCollection;

public class WriteUtils {
    public void writeIntoText(PCollection<String> input, String path, String suffix) {
        input.apply(TextIO.write().to(path).withSuffix(suffix));
    }

    public void writeIntoParquet(PCollection<GenericRecord> input, String path, Schema schema) {
        input.apply(FileIO.<GenericRecord>write()
                        .via(ParquetIO.sink(schema))
                        .to("path").withSuffix(".parquet"));
    }

    public void writeOneFilePerWindow(PCollection<String> input, String filenamePrefix, int numShards) {
        input.apply(new WriteOneFilePerWindow(filenamePrefix, numShards));

    }
}
