package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.producer.model.avro.NotificationEvent;

@Service
public class ConvertFlow {

  @Getter
  private final Flow<Integer, NotificationEvent, NotUsed> flow;

  public ConvertFlow() {
    flow = Flow.of(Integer.class)
        .map(this::convert)
        .named("serialization-flow");
  }

  private NotificationEvent convert(Integer i) {
    return new NotificationEvent(String.format("Id: %d", i), String.format("Body: %d", i));
  }
}
