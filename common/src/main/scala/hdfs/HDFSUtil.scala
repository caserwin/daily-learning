package hdfs

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem

/**
  * Created by yidxue on 2018/9/7
  */
object HDFSUtil {

  def main(args: Array[String]): Unit = {
    println(dirExists("/user"))
  }

  def dirExists(hdfsDirectory: String): Boolean = {
    val hadoopConf = new Configuration()
    hadoopConf.set("fs.default.name", "hdfs://localhost:9000")
    val fs = FileSystem.get(hadoopConf)
    fs.exists(new org.apache.hadoop.fs.Path(hdfsDirectory))
  }
}
