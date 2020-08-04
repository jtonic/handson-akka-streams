package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.stream.javadsl.Sink;
import org.springframework.stereotype.Component;

@Component
public class DataSink {

  public final Sink<String, ?> build() {
    return Sink.foreach(System.out::println);
  }
}
