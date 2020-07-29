package ro.jtonic.handson.akka.streams;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Worker implements CommandLineRunner {

  private final Materializer materializer;
  private final ActorSystem actorSystem;

  public Worker(Materializer materializer, ActorSystem actorSystem) {
    this.materializer = materializer;
    this.actorSystem = actorSystem;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("E-core is running.\nPress RETURN to stop...");
    System.in.read();
    release();
  }

  private void release() {
    System.out.println("Releasing resources (materializer and actor systems)...");
    materializer.shutdown();
    actorSystem.terminate();
    System.out.println("Resources have been released.");
  }
}
