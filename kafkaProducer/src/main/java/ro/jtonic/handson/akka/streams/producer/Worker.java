package ro.jtonic.handson.akka.streams.producer;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.producer.flow.MainFlow;

import java.util.concurrent.CompletionStage;

@Service
public class Worker implements CommandLineRunner {

  private final Materializer materializer;
  private final ActorSystem actorSystem;
  private final MainFlow mainFlow;

  public Worker(
          Materializer materializer,
          ActorSystem actorSystem,
          MainFlow mainFlow) {

    this.materializer = materializer;
    this.actorSystem = actorSystem;
    this.mainFlow = mainFlow;
  }

  @Override
  public void run(String... args) {
    final CompletionStage<Done> run = mainFlow.run();
    run.thenAccept(this::release);
  }

  private void release(Done done) {
    System.out.println("Releasing resources (materializer and actor systems)...");
    materializer.shutdown();
    actorSystem.terminate();
    System.out.println("Resources have been released.");
  }
}
