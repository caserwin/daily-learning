package datastream.activeuser;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Properties;

/**
 * @author yiding
 */
public class ActiveUsersMain {

    public static void main(String[] args) throws Exception {
        Config config = ConfigFactory.load();
        Properties props = new Properties();
        props.setProperty("input-topic", config.getString("kafka.topic"));
        props.setProperty("output-topic", config.getString("sink_method.kafka.topic"));
        props.setProperty("input.bootstrap.servers", config.getString("kafka.bootstrap_servers"));
        props.setProperty("output.bootstrap.servers", config.getString("sink_method.kafka.bootstrap_servers"));
        props.setProperty("group.id", config.getString("kafka.group_id"));

        int parallelism = 1;
        new ActiveUsersApp().run(props, parallelism);
    }
}
