package demo;

import demo.service.Constant;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

/**
 * Created by yidxue on 2018/8/31
 */
public class Demo1 {

    public static void main(String[] args) throws Exception {

        String inputPath = "beam/src/main/resources/demo.text";
        String outputPath = "beam/result/demo1";
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
        Pipeline pipeline = Pipeline.create(options);

        //from CSV
        PCollection<String> input1 = pipeline.apply(TextIO.read().from(inputPath));
        input1
            .apply("ExtractWords", ParDo.of(new DoFn<String, String>() {
                @ProcessElement
                public void processElement(ProcessContext c) {
                    for (String word : c.element().split(Constant.BLANK)) {
                        if (!word.isEmpty()) {
                            c.output(word);
                        }
                    }
                }
            }))
            .apply(Count.perElement())
            .apply("FormatResult", MapElements.via(new SimpleFunction<KV<String, Long>, String>() {
                @Override
                public String apply(KV<String, Long> input) {
                    System.out.println("demo part1:read from text and count words: " + input.getKey() + ":" + input.getValue());
                    return input.getKey() + ":" + input.getValue();
                }
            }));

        //写CSV文件
        input1.apply(TextIO.write().to(outputPath).withSuffix(".csv"));

        PipelineResult result = pipeline.run();
        try {
            result.waitUntilFinish();
        } catch (Exception exc) {
            result.cancel();
        }
    }
}

