package ro.jtonic.handson.akka.streams.consumer.conf;


import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import com.typesafe.config.Config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

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
  public ConsumerSettings<UUID, String> kafkaConsumerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.consumer");
    return ConsumerSettings.create(config, new UUIDDeserializer(), new StringDeserializer())
        .withBootstrapServers(kafkaBootstrapServers)
        .withGroupId(groupId)
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        .withStopTimeout(Duration.ofSeconds(5));
  }
}
