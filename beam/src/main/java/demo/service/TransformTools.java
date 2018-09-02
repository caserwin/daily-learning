package demo.service;

import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.JsonToRow;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.commons.csv.CSVFormat;
import org.joda.time.Duration;
import java.util.Map;
import static org.apache.beam.sdk.extensions.sql.impl.schema.BeamTableUtils.beamRow2CsvLine;

/**
 */
public class TransformTools extends DoFn{
    public Schema makeSchema(Map<String,Schema.FieldType> fields){
        Schema.Builder builder= Schema.builder();
        for (String fieldname:fields.keySet()) {
            builder=builder.addNullableField(fieldname,fields.get(fieldname));
        }
        Schema schema=builder.build();
        return schema;
    }
    public PCollection<String> AnalyseJsonBySchema(PCollection<String> input, Schema schema) {
        return input.apply(JsonToRow.withSchema(schema))
                .apply(ParDo.<Row, String>of(new DoFn<Row, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) throws Exception {
                        c.output(c.element().toString());
                    }
                }));
    }
    public void SetCsvFormate(PCollection<Row> input, CSVFormat csvFormat) {
        input.apply(ParDo.<Row, String>of(new DoFn<Row, String>() {
            @ProcessElement
            public void processElement(ProcessContext c) throws Exception {

                Row in = c.element();

//                        c.output(in.getInt64(0).toString());
//                        c.output(in.getValues().toString());
                c.output(beamRow2CsvLine(in, csvFormat));
            }
        }));
    }
    public PCollection<KV<Long,String>> ReadWithFixedWindowsAndTrigger(PCollection<KV<Long,String>> input){
        return input.apply(Window.<KV<Long,String>>into(FixedWindows.of(Duration.standardMinutes(1)))
                .triggering(AfterProcessingTime.pastFirstElementInPane()
                        .plusDelayOf(Duration.standardMinutes(1)))
                .discardingFiredPanes()
                .withAllowedLateness(Duration.standardMinutes(1)));
    }
    public PCollection<String> getValueAndFormatByUtf8(PCollection<KV<Long,String>> input){
        return input.apply(Values.<String>create()).setCoder(StringUtf8Coder.of());
    }
}
