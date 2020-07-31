package ro.jtonic.handson.akka.streams.consumer;

import akka.stream.javadsl.Sink;
import org.springframework.stereotype.Component;

@Component
public class ChannelSink {

  public final Sink<String, ?> build() {
    return Sink.foreach(System.out::println);
  }
}
