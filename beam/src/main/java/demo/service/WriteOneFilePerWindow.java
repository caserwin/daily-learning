package demo.service;

import org.apache.beam.sdk.io.FileBasedSink;
import org.apache.beam.sdk.io.FileBasedSink.OutputFileHints;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.fs.ResolveOptions;
import org.apache.beam.sdk.io.fs.ResourceId;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;
import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.transforms.windowing.PaneInfo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import javax.annotation.Nullable;

import static com.google.common.base.Objects.firstNonNull;

public class WriteOneFilePerWindow extends PTransform<PCollection<String>, PDone> {
    private static final DateTimeFormatter FORMATTER = ISODateTimeFormat.hourMinute();
    private String filenamePrefix;
    @Nullable
    private Integer numShards;

    public WriteOneFilePerWindow(String filenamePrefix, Integer numShards) {
        this.filenamePrefix = filenamePrefix;
        this.numShards = numShards;
    }

    @Override
    public PDone expand(PCollection<String> input) {
        ResourceId resource = FileBasedSink.convertToFileResourceIfPossible(filenamePrefix);
        TextIO.Write write =
                TextIO.write()
                        .to(new PerWindowFiles(resource))
                        .withTempDirectory(resource.getCurrentDirectory())
                        .withWindowedWrites();
        if (numShards != null) {
            write = write.withNumShards(numShards);
        }
        return input.apply(write);
    }

    public static class PerWindowFiles extends FileBasedSink.FilenamePolicy {

        private final ResourceId baseFilename;

        public PerWindowFiles(ResourceId baseFilename) {
            this.baseFilename = baseFilename;
        }

        public String filenamePrefixForWindow(IntervalWindow window) {
            String prefix =
                    baseFilename.isDirectory() ? "" : firstNonNull(baseFilename.getFilename(), "");
            return String.format(
                    "%s-%s-%s", prefix, FORMATTER.print(window.start()), FORMATTER.print(window.end()));
        }

        @Override
        public ResourceId windowedFilename(
                int shardNumber,
                int numShards,
                BoundedWindow window,
                PaneInfo paneInfo,
                OutputFileHints outputFileHints) {
            IntervalWindow intervalWindow = (IntervalWindow) window;
            String filename =
                    String.format(
                            "%s-%s-of-%s%s",
                            filenamePrefixForWindow(intervalWindow),
                            shardNumber,
                            numShards,
                            outputFileHints.getSuggestedFilenameSuffix());
            return baseFilename
                    .getCurrentDirectory()
                    .resolve(filename, ResolveOptions.StandardResolveOptions.RESOLVE_FILE);
        }

        @Override
        public ResourceId unwindowedFilename(
                int shardNumber, int numShards, OutputFileHints outputFileHints) {
            throw new UnsupportedOperationException("Unsupported.");
        }
    }
}
