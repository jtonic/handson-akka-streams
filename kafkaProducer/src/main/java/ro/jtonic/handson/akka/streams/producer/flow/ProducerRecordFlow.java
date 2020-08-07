package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import java.util.UUID;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.model.avro.NotificationEvent;

@Service
public class ProducerRecordFlow {

  @Getter
  private final Flow<NotificationEvent, ProducerRecord<UUID, Object>, NotUsed> flow;

  @Value("${jtonic.akka-streams.kafka.topic}")
  private String topic;

  public ProducerRecordFlow() {
    flow = Flow.of(NotificationEvent.class)
        .map(value -> new ProducerRecord<UUID, Object>(topic, UUID.randomUUID(), value))
        .named("kafka-producer-record");
  }
}
