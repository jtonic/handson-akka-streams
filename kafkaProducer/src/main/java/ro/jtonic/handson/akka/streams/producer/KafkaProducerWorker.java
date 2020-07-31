package ro.jtonic.handson.akka.streams.producer;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerWorker implements CommandLineRunner {

  private final Materializer materializer;
  private final ActorSystem actorSystem;
  private final KafkaProducerMainFlow kafkaProducerMainFlow;

  public KafkaProducerWorker(
      Materializer materializer,
      ActorSystem actorSystem,
      KafkaProducerMainFlow kafkaProducerMainFlow) {

    this.materializer = materializer;
    this.actorSystem = actorSystem;
    this.kafkaProducerMainFlow = kafkaProducerMainFlow;
  }

  @Override
  public void run(String... args) {
    final CompletionStage<Done> run = kafkaProducerMainFlow.run();
    run.thenAccept((done -> release()));
  }

  private void release() {
    System.out.println("Releasing resources (materializer and actor systems)...");
    materializer.shutdown();
    actorSystem.terminate();
    System.out.println("Resources have been released.");
  }
}
