package ro.jtonic.handson.akka.streams.producer.flow;

import akka.Done;
import akka.stream.Materializer;
import akka.stream.javadsl.Keep;
import java.util.concurrent.CompletionStage;
import org.springframework.stereotype.Service;

@Service
public class MainFlow {

  private final Materializer materializer;
  private final DataSink dataSink;
  private final DataSource dataSource;
  private final ConvertFlow convertFlow;
  private final BusyFlow busyFlow;
  private final ProducerRecordFlow producerRecordFlow;

  public MainFlow(Materializer materializer,
                  DataSink dataSink,
                  DataSource dataSource,
                  ConvertFlow convertFlow,
                  BusyFlow busyFlow,
                  ProducerRecordFlow producerRecordFlow) {
    this.materializer = materializer;
    this.dataSink = dataSink;
    this.dataSource = dataSource;
    this.convertFlow = convertFlow;
    this.busyFlow = busyFlow;
    this.producerRecordFlow = producerRecordFlow;
  }


  public CompletionStage<Done> run() {
    return dataSource.getSource()
            .via(convertFlow.getFlow())
            .via(busyFlow.getFlow())
            .via(producerRecordFlow.getFlow())
            .toMat(dataSink.getSink(), Keep.right())
            .named("ecore")
            .run(materializer);
//            .runWith(dataSink.getSink(), materializer);
  }
}
