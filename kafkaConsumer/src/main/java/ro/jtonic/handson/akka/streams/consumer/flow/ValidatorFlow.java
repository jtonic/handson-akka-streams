package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.NotUsed;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.stream.javadsl.Flow;
import org.springframework.stereotype.Component;

@Component
public class ValidatorFlow {

  public Flow<CommittableMessage<String, String>, String, NotUsed> build() {
    return Flow.fromFunction(cm -> cm.record().value());
  }
}
