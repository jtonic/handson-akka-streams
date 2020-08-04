package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.NotUsed;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidatorFlow {

  @Getter
  private final Flow<CommittableMessage<UUID, String>, String, NotUsed> flow;

  public ValidatorFlow() {
    flow = Flow.fromFunction(cm -> {
      final ConsumerRecord<UUID, String> record = cm.record();
      System.out.printf("Filtered message: \n\ttopic: %s, \n\toffset: %d, \n\tkey: %s, \n\tmessage = %s\n",
              record.topic(),
              record.offset(),
              record.key().toString(),
              record.value());
      return record.value();
    });
  }
}
