package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.NotUsed;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ValidatorFlow {

  @Getter
  private final Flow<CommittableMessage<String, String>, String, NotUsed> flow;

  public ValidatorFlow() {
    flow = Flow.fromFunction(cm -> cm.record().value());
  }
}
