package ro.jtonic.handson.akka.streams.producer.flow;

import akka.Done;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.javadsl.Sink;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Service
public class DataSink {

  @Getter
  private final Sink<ProducerRecord<UUID, String>, CompletionStage<Done>> sink;

  public DataSink(ProducerSettings<UUID, String> producerSettings) {
    this.sink = Producer.plainSink(producerSettings).named("kafka-data-sink");
  }
}
