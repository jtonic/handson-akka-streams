package ro.jtonic.handson.akka.streams.producer.conf;

import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import com.typesafe.config.Config;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

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
  public ProducerSettings<UUID, String> producerSettings() {
    final Config config = actorSystem.settings().config().getConfig("akka.kafka.producer");
    return ProducerSettings.create(config, new UUIDSerializer(), new StringSerializer())
        .withBootstrapServers(bootstrapServers);
  }
}
