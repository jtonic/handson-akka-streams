package ro.jtonic.handson.akka.streams.consumer.flow;

import akka.Done;
import akka.actor.ActorSystem;
import akka.kafka.CommitterSettings;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.kafka.javadsl.Committer;
import akka.kafka.javadsl.Consumer;
import akka.stream.Materializer;
import akka.stream.javadsl.Keep;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.common.eip.PassThroughFlow;

@Service
public class MainFlow {

  private final KafkaSource kafkaSource;
  private final DataSink dataSink;
  private final ConversionFlow conversionFlow;
  private final Materializer materializer;
  private final ActorSystem actorSystem;

  public MainFlow(
      KafkaSource kafkaSource,
      DataSink dataSink,
      ConversionFlow conversionFlow,
      Materializer materializer,
      ActorSystem actorSystem) {

    this.kafkaSource = kafkaSource;
    this.dataSink = dataSink;
    this.conversionFlow = conversionFlow;
    this.materializer = materializer;
    this.actorSystem = actorSystem;
  }

  public Consumer.DrainingControl<Done> run() {

    CommitterSettings committerSettings = CommitterSettings.create(actorSystem);
    final Consumer.DrainingControl<Done> run = kafkaSource.getSource()
            .via(PassThroughFlow.create(conversionFlow.getFlow(), Keep.right()))
            .map(CommittableMessage::committableOffset)
            .toMat(Committer.sink(committerSettings), Keep.both())
            .mapMaterializedValue(Consumer::createDrainingControl)
            .run(materializer);
    return run;
  }
}
