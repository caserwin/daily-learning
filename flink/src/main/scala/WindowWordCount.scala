import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * https://ci.apache.org/projects/flink/flink-docs-release-1.3/dev/datastream_api.html
  *
  * To run the example program, start the input stream with netcat first from a terminal:
  * nc -lk 9998
  */
object WindowWordCount {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9998)

    val counts = text.flatMap { _.toLowerCase.split("\\W+")filter{_.nonEmpty}}
      // 可以简写为 .map {(_,1)}
      .map {str =>(str,1)}
      // .map {(_,1)}
      .keyBy(0)
      // 每5秒作为一个间隔去词频统计
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print

    env.execute("Window Stream WordCount")
  }
}
