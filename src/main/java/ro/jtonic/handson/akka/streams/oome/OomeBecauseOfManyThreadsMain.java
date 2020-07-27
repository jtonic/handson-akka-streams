package ro.jtonic.handson.akka.streams.oome;

import java.util.concurrent.TimeUnit;

public class OomeBecauseOfManyThreadsMain {

  private static long no = 0L;

  public static void main(String[] args) {

   for (int a = 0; a < 1_000_000; a++ ) {
     new Thread(() -> {
       try {
         TimeUnit.SECONDS.sleep(5);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
     }, "worker - " + ++no).start();
   }
  }
}
