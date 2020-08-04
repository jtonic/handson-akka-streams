package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.kafka.javadsl.Consumer.Control;
import akka.stream.javadsl.Source;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaSource {

  @Getter
  private final Source<CommittableMessage<UUID, String>, Control> source;

  public KafkaSource(
      ConsumerSettings<UUID, String> kafkaConsumerSettings,
      @Value("${jtonic.akka-streams.kafka.topic}") String topic) {

    this.source = Consumer
            .committableSource(kafkaConsumerSettings, Subscriptions.topics(topic));
  }
}
