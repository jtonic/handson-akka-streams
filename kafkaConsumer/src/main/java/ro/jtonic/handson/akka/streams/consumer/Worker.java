package ro.jtonic.handson.akka.streams.consumer;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class Worker implements CommandLineRunner {

  @Autowired
  private Materializer materializer;
  @Autowired
  private ActorSystem actorSystem;

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
