package ro.jtonic.handson.akka.streams.producer.conf;

import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConf {

  private final ActorSystem actorSystem;
  private final String bootstrapServers;
  private final String schemaRegistryUrl;

  public KafkaProducerConf(
      ActorSystem actorSystem,
      @Value("${jtonic.akka-streams.kafka.bootstrap-servers}") String bootstrapServers,
      @Value("${jtonic.akka-streams.kafka.schema-registry-url}") String schemaRegistryUrl) {
    this.actorSystem = actorSystem;
    this.bootstrapServers = bootstrapServers;
    this.schemaRegistryUrl = schemaRegistryUrl;
  }

  @Bean
  public ProducerSettings<UUID, Object> producerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.producer");
    return ProducerSettings
              .create(config, new UUIDSerializer(), createSerializer())
              .withBootstrapServers(bootstrapServers);
  }

  private Serializer<Object> createSerializer() {
    Map<String, Object> kafkaAvroSerDeConfig = ImmutableMap.of(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    KafkaAvroSerializer kafkaAvroSerializer = new KafkaAvroSerializer();
    kafkaAvroSerializer.configure(kafkaAvroSerDeConfig, false);
    return kafkaAvroSerializer;
  }
}
