package ro.jtonic.handson.akka.streams.producer.conf;

import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import com.typesafe.config.Config;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import java.util.Collections;
import java.util.UUID;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.jtonic.handson.akka.streams.common.model.Notification;

@Configuration
public class KafkaProducerConf {

  private final ActorSystem actorSystem;
  private final String bootstrapServers;

  public KafkaProducerConf(
      ActorSystem actorSystem,
      @Value("${jtonic.akka-streams.kafka.bootstrap-servers}") String bootstrapServers) {
    this.actorSystem = actorSystem;
    this.bootstrapServers = bootstrapServers;
  }

  @Bean
  public ProducerSettings<UUID, Notification> producerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.producer");
    final KafkaJsonSerializer<Notification> valueSerializer = new KafkaJsonSerializer<>();
    valueSerializer.configure(Collections.emptyMap(), false);
    return ProducerSettings.create(config, new UUIDSerializer(),
        valueSerializer)
        .withBootstrapServers(bootstrapServers);
  }
}
