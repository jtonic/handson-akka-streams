package ro.jtonic.handson.akka.streams.consumer.conf;


import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import com.typesafe.config.Config;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConf {

  private final ActorSystem actorSystem;
  private final String kafkaBootstrapServers;
  private final String groupId;
  private final String schemaRegistryUrl;

  public KafkaConsumerConf(
      ActorSystem actorSystem,
      @Value("${jtonic.akka-streams.kafka.bootstrap-servers}") String kafkaBootstrapServers,
      @Value("${jtonic.akka-streams.kafka.group-id}") String groupId,
      @Value("${jtonic.akka-streams.kafka.schema-registry-url}") String schemaRegistryUrl) {

    this.actorSystem = actorSystem;
    this.kafkaBootstrapServers = kafkaBootstrapServers;
    this.groupId = groupId;
    this.schemaRegistryUrl = schemaRegistryUrl;
  }

  @Bean
  public ConsumerSettings<UUID, Object> kafkaConsumerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.consumer");

    return ConsumerSettings.create(config, new UUIDDeserializer(), createDeserializer())
        .withBootstrapServers(kafkaBootstrapServers)
        .withGroupId(groupId)
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        .withStopTimeout(Duration.ofSeconds(5));
  }

  private Deserializer<Object> createDeserializer() {
    Map<String, Object> kafkaAvroSerDeConfig = new HashMap<>();
    kafkaAvroSerDeConfig.put(
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    KafkaAvroDeserializer kafkaAvroDeserializer = new KafkaAvroDeserializer();
    kafkaAvroDeserializer.configure(kafkaAvroSerDeConfig, false);
    return kafkaAvroDeserializer;
  }
}
