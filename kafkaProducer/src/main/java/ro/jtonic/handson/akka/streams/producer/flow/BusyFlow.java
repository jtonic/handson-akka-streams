package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.Busy;
import ro.jtonic.handson.akka.streams.common.model.avro.NotificationEvent;

@Service
public class BusyFlow {

  @Getter
  private final Flow<NotificationEvent, NotificationEvent, NotUsed> flow;

  public BusyFlow(
         @Value("${jtonic.busy-duration}") long busyDuration
  ) {
    flow = Flow.of(NotificationEvent.class)
        .map(s -> {
          Busy.busy(Duration.ofMillis(busyDuration));
          return s;
        }).named("busy-flow");
  }
}
