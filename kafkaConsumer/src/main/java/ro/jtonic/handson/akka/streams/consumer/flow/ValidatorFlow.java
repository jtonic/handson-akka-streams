package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.NotUsed;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.stream.javadsl.Flow;
import java.util.UUID;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.model.Notification;

@Service
public class ValidatorFlow {

  @Getter
  private final Flow<CommittableMessage<UUID, Notification>, Notification, NotUsed> flow;

  public ValidatorFlow() {
    flow = Flow.fromFunction(cm -> {
      final ConsumerRecord<UUID, Notification> record = cm.record();
      System.out.printf("Filtered message: \n\ttopic: %s, \n\toffset: %d, \n\tkey: %s, \n\tnotification = %s\n",
              record.topic(),
              record.offset(),
              record.key().toString(),
              record.value().toString());
      return record.value();
    });
  }
}
