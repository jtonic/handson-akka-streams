package ro.jtonic.handson.akka.streams.consumer;

import akka.actor.ActorSystem;
import akka.kafka.CommitterSettings;
import akka.kafka.ConsumerMessage.CommittableMessage;
import akka.kafka.javadsl.Committer;
import akka.kafka.javadsl.Consumer;
import akka.stream.Materializer;
import akka.stream.javadsl.Keep;
import org.springframework.stereotype.Component;
import ro.jtonic.handson.akka.streams.common.eip.PassThroughFlow;

@Component
public class EcoreFlow {

  private final KafkaSource kafkaSource;
  private final ChannelSink channelSink;
  private final ValidatorFlow validatorFlow;
  private final Materializer materializer;
  private final ActorSystem actorSystem;

  public EcoreFlow(
      KafkaSource kafkaSource,
      ChannelSink channelSink,
      ValidatorFlow validatorFlow,
      Materializer materializer,
      ActorSystem actorSystem) {

    this.kafkaSource = kafkaSource;
    this.channelSink = channelSink;
    this.validatorFlow = validatorFlow;
    this.materializer = materializer;
    this.actorSystem = actorSystem;
  }

  public void run() {

    CommitterSettings committerSettings = CommitterSettings.create(actorSystem);
    kafkaSource
        .build()
        .via(PassThroughFlow.create(validatorFlow.build(), Keep.right()))
        .map(CommittableMessage::committableOffset)
        .toMat(Committer.sink(committerSettings), Keep.both())
        .mapMaterializedValue(Consumer::createDrainingControl)
        .run(materializer);
  }
}
