package ro.jtonic.handson.akka.streams.producer;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {

  private UUID id;
  private String subject;
  private String body;
}
