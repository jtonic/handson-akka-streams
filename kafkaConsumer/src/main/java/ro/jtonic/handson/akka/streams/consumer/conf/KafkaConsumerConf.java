package ro.jtonic.handson.akka.streams.consumer.conf;


import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import com.typesafe.config.Config;
import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.jtonic.handson.akka.streams.common.model.Notification;

@Configuration
public class KafkaConsumerConf {

  private final ActorSystem actorSystem;
  private final String kafkaBootstrapServers;
  private final String groupId;

  public KafkaConsumerConf(
          ActorSystem actorSystem,
          @Value("${jtonic.akka-streams.kafka.bootstrap-servers}") String kafkaBootstrapServers,
          @Value("${jtonic.akka-streams.kafka.group-id}") String groupId
  ) {

    this.actorSystem = actorSystem;
    this.kafkaBootstrapServers = kafkaBootstrapServers;
    this.groupId = groupId;
  }

  @Bean
  public ConsumerSettings<UUID, Notification> kafkaConsumerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.consumer");

    return ConsumerSettings.create(config, new UUIDDeserializer(), createKafkaJsonDeserializer())
        .withBootstrapServers(kafkaBootstrapServers)
        .withGroupId(groupId)
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        .withStopTimeout(Duration.ofSeconds(5));
  }

  private KafkaJsonDeserializer<Notification> createKafkaJsonDeserializer() {
    final KafkaJsonDeserializer<Notification> jsonDeserializer = new KafkaJsonDeserializer<>();
    Map<String, String> props = new HashMap<>();
    props.put("json.value.type", Notification.class.getName()); //fixme jtonic: externalize this configuration
    jsonDeserializer.configure(props, false);
    return jsonDeserializer;
  }
}
