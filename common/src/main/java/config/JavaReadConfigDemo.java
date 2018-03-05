package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by yidxue on 2018/3/5
 */
public class JavaReadConfigDemo {
    /**
     * 默认读取 application.conf 文件
     **/
    private static Config appConfig = ConfigFactory.load();

    /**
     * 设置读取 apptest.conf 文件
     **/
    private static Config appTestConfig = ConfigFactory.parseResources("apptest.conf");

    /**
     * 同时读取 apptest.conf 和 application.conf 文件
     **/
    private static Config allConfig = ConfigFactory.load().withFallback(appTestConfig).resolve();

    public static void main(String[] args) {
        System.out.println(appConfig.getString("kafka_producer.bootstrap_servers"));
        System.out.println(appTestConfig.getString("app.test"));
        System.out.println("===================================");
        System.out.println(allConfig.getString("kafka_producer.bootstrap_servers"));
        System.out.println(allConfig.getString("app.test"));
    }
}
