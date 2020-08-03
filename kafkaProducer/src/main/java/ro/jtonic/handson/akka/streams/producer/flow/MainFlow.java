package ro.jtonic.handson.akka.streams.producer.flow;

import akka.Done;
import akka.stream.Materializer;
import akka.stream.javadsl.Keep;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionStage;

@Service
public class MainFlow {

  private final Materializer materializer;
  private final DataSink dataSink;
  private final DataSource dataSource;
  private final SerializerFlow serializerFlow;
  private final BusyFlow busyFlow;
  private final ProducerRecordFlow producerRecordFlow;

  public MainFlow(Materializer materializer,
                  DataSink dataSink,
                  DataSource dataSource,
                  SerializerFlow serializerFlow,
                  BusyFlow busyFlow,
                  ProducerRecordFlow producerRecordFlow) {
    this.materializer = materializer;
    this.dataSink = dataSink;
    this.dataSource = dataSource;
    this.serializerFlow = serializerFlow;
    this.busyFlow = busyFlow;
    this.producerRecordFlow = producerRecordFlow;
  }


  public CompletionStage<Done> run() {
    return dataSource.getSource()
            .via(serializerFlow.getFlow())
            .via(busyFlow.getFlow())
            .via(producerRecordFlow.getFlow())
            .toMat(dataSink.getSink(), Keep.right())
            .named("ecore")
            .run(materializer);
//            .runWith(dataSink.getSink(), materializer);
  }
}
