package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.Busy;

import java.time.Duration;

@Service
public class BusyFlow {

  @Getter
  private final Flow<String, String, NotUsed> flow;

  public BusyFlow(
         @Value("${jtonic.busy-duration}") long busyDuration
  ) {
    flow = Flow.of(String.class)
        .map(s -> {
          Busy.busy(Duration.ofMillis(busyDuration));
          return s;
        }).named("busy-flow");
  }
}
