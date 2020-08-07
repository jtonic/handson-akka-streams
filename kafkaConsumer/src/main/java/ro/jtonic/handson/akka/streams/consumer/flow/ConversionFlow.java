package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.NotUsed;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.stream.javadsl.Flow;
import java.util.UUID;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.model.avro.NotificationEvent;

@Service
public class ConversionFlow {

  @Getter
  private final Flow<CommittableMessage<UUID, Object>, NotificationEvent, NotUsed> flow;

  public ConversionFlow() {
    flow = Flow.fromFunction(cm -> {
      final ConsumerRecord<UUID, Object> record = cm.record();
      final Object recordValue = record.value();
      NotificationEvent ne = (NotificationEvent) recordValue;
      System.out.printf("Filtered message: \n\ttopic: %s, \n\toffset: %d, \n\tkey: %s, \n\tnotification = %s\n",
              record.topic(),
              record.offset(),
              record.key().toString(),
              ne.toString());
      return ne;
    });
  }
}
