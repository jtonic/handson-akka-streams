package ro.jtonic.handson.akka.streams.common.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Notification {

    public final String id;
    public final String body;

  @JsonCreator
  public Notification(
      @JsonProperty("id") String id,
      @JsonProperty("body") String body) {
    this.id = id;
    this.body = body;
  }
}
