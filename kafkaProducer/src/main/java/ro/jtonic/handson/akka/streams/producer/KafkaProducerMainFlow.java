package ro.jtonic.handson.akka.streams.producer;

import akka.Done;
import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerMainFlow {

  private final Materializer materializer;
  private final KafkaSink kafkaSink;
  private final DataProducer dataProducer;
  private final DataSerializerFlow dataSerializerFlow;
  private final KafkaProducerRecordFlow kafkaProducerRecordFlow;

  public KafkaProducerMainFlow(Materializer materializer,
                               KafkaSink kafkaSink,
                               DataProducer dataProducer,
                               DataSerializerFlow dataSerializerFlow,
                               KafkaProducerRecordFlow kafkaProducerRecordFlow) {
    this.materializer = materializer;
    this.kafkaSink = kafkaSink;
    this.dataProducer = dataProducer;
    this.dataSerializerFlow = dataSerializerFlow;
    this.kafkaProducerRecordFlow = kafkaProducerRecordFlow;
  }


  CompletionStage<Done> run() {
    return dataProducer.getSource()
            .via(dataSerializerFlow.getFlow())
            .via(kafkaProducerRecordFlow.getFlow())
            .runWith(kafkaSink.getSink(), materializer);
  }
}
