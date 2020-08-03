package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class SerializerFlow {

  @Getter
  private final Flow<Integer, String, NotUsed> flow;

  public SerializerFlow() {
    flow = Flow.of(Integer.class)
        .map(Object::toString)
        .named("serialization-flow");
  }
}
