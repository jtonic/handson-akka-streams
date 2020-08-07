package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.kafka.javadsl.Consumer.Control;
import akka.stream.javadsl.Source;
import java.util.UUID;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaSource {

  @Getter
  private final Source<CommittableMessage<UUID, Object>, Control> source;

  public KafkaSource(
      ConsumerSettings<UUID, Object> kafkaConsumerSettings,
      @Value("${jtonic.akka-streams.kafka.topic}") String topic) {

    this.source = Consumer
            .committableSource(kafkaConsumerSettings, Subscriptions.topics(topic));
  }
}
