package ro.jtonic.handson.akka.streams.producer.flow;

import akka.Done;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.javadsl.Sink;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.model.Notification;

@Service
public class DataSink {

  @Getter
  private final Sink<ProducerRecord<UUID, Notification>, CompletionStage<Done>> sink;

  public DataSink(ProducerSettings<UUID, Notification> producerSettings) {
    this.sink = Producer.plainSink(producerSettings).named("kafka-data-sink");
  }
}
