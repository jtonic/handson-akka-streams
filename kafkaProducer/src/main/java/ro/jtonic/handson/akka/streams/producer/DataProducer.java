package ro.jtonic.handson.akka.streams.producer;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class DataProducer {

  @Getter
  private final Source<Integer, NotUsed> source;

  public DataProducer() {
    source = Source.range(1, 300);
  }
}
