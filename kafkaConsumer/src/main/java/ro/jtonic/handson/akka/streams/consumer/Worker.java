package ro.jtonic.handson.akka.streams.consumer;

import akka.Done;
import akka.actor.ActorSystem;
import akka.kafka.javadsl.Consumer;
import akka.stream.Materializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ro.jtonic.handson.akka.streams.consumer.flow.MainFlow;

@Service
public class Worker implements CommandLineRunner {

  private final Materializer materializer;
  private final ActorSystem actorSystem;
  private final MainFlow mainFlow;

  public Worker(Materializer materializer, ActorSystem actorSystem, MainFlow mainFlow) {
    this.materializer = materializer;
    this.actorSystem = actorSystem;
    this.mainFlow = mainFlow;
  }

  @Override
  public void run(String... args) throws Exception {

    System.out.println("E-core is running.\nPress RETURN to stop...");

    final Consumer.DrainingControl<Done> run = mainFlow.run();

    System.in.read();
    release(run);
  }

  private void release(Consumer.DrainingControl<Done> run) {
    System.out.println("Releasing resources (materializer and actor systems)...");
    run.shutdown();
    materializer.shutdown();
    actorSystem.terminate();
    System.out.println("Resources have been released.");
  }
}
