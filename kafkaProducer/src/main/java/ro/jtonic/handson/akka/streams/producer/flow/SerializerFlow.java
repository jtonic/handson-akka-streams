package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.model.Notification;

@Service
public class SerializerFlow {

  @Getter
  private final Flow<Integer, Notification, NotUsed> flow;

  public SerializerFlow() {
    flow = Flow.of(Integer.class)
        .map(this::doSerialize)
        .named("serialization-flow");
  }

  private Notification doSerialize(Integer i) {
    return new Notification(String.format("Id: %d", i), String.format("Body: %d", i));
  }
}
