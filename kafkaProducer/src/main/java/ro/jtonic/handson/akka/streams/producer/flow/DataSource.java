package ro.jtonic.handson.akka.streams.producer.flow;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataSource {

  @Getter
  private final Source<Integer, NotUsed> source;

  public DataSource(
          @Value("${jtonic.messages-no}") int messagesNo
  ) {
    source = Source.range(1, messagesNo).named("data-source");
  }
}
