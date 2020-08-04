package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.stream.javadsl.Sink;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class DataSink {

  @Getter
  private final Sink<String, ?> sink;

  public DataSink() {
    sink = Sink.foreach(System.out::println);
  }
}
