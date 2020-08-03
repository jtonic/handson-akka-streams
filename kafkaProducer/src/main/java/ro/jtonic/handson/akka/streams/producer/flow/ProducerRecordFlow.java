package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProducerRecordFlow {

  @Getter
  private final Flow<String, ProducerRecord<UUID, String>, NotUsed> flow;

  @Value("${jtonic.akka-streams.kafka.topic}")
  private String topic;

  public ProducerRecordFlow() {
    flow = Flow.of(String.class)
        .map(value -> new ProducerRecord<>(topic, UUID.randomUUID(), value))
        .named("kafka-producer-record");
  }
}