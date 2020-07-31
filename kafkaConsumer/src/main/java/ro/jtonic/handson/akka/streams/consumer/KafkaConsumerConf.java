package ro.jtonic.handson.akka.streams.consumer;


import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConf {

  private final ActorSystem actorSystem;
  private final String kafkaBootstrapServers;
  private final String groupId;
  private final String topic;

  public KafkaConsumerConf(
      ActorSystem actorSystem,
      @Value("${jtonic.akka-streams.kafka-bootstrap-servers}") String kafkaBootstrapServers,
      @Value("${jtonic.akka-streams.group-id}") String groupId,
      @Value("${jtonic.akka-streams.topic}") String topic
      ) {

    this.actorSystem = actorSystem;
    this.kafkaBootstrapServers = kafkaBootstrapServers;
    this.groupId = groupId;
    this.topic = topic;
  }

  @Bean
  public ConsumerSettings<String, String> kafkaConsumerSettings() {
    return ConsumerSettings.create(actorSystem, new StringDeserializer(), new StringDeserializer())
        .withBootstrapServers(kafkaBootstrapServers)
        .withGroupId(groupId)
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        .withStopTimeout(Duration.ofSeconds(5));
  }

  @Bean
  public KafkaSource kafkaSource() {
    return new KafkaSource(kafkaConsumerSettings(), topic);
  }
}
