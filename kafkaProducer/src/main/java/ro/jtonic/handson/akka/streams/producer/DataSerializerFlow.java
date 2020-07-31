package ro.jtonic.handson.akka.streams.producer;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class DataSerializerFlow {

  @Getter
  private final Flow<Integer, String, NotUsed> flow;

  public DataSerializerFlow() {
    flow = Flow.of(Integer.class)
        .map(Object::toString);
  }
}
