package ro.jtonic.handson.akka.streams.producer;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import java.util.UUID;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerRecordFlow {

  @Getter
  private final Flow<Notification, ProducerRecord<UUID, Notification>, NotUsed> flow;

  @Value("${jtonic.akka-streams.topic}")
  private String topic;

  public KafkaProducerRecordFlow() {
    flow = Flow.of(Notification.class)
        .map(notif -> new ProducerRecord<UUID, Notification>(topic, notif.getId(), notif));
  }
}
