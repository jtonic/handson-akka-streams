package ro.jtonic.handson.akka.streams.consumer;

import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.kafka.javadsl.Consumer.Control;
import akka.stream.javadsl.Source;

public class KafkaSource {

  private final String topic;
  private final ConsumerSettings<String, String> kafkaConsumerSettings;

  public KafkaSource(
      ConsumerSettings<String, String> kafkaConsumerSettings,
      String topic) {

    this.kafkaConsumerSettings = kafkaConsumerSettings;
    this.topic = topic;
  }

  public Source<CommittableMessage<String, String>, Control> build() {
    return Consumer
        .committableSource(kafkaConsumerSettings, Subscriptions.topics(topic));
  }
}
