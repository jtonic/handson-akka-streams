package ro.jtonic.handson.akka.streams.producer;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import java.util.UUID;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class DataSerializerFlow {

  @Getter
  private final Flow<Integer, Notification, NotUsed> flow;

  public DataSerializerFlow() {
    flow = Flow.of(Integer.class)
        .map(i -> {
              final UUID id = UUID.randomUUID();
          return Notification.builder()
                  .id(id)
                  .subject("Subject - " + id)
                  .body("Body - " + id)
                  .build();
            }
        );
  }
}
