package ro.jtonic.handson.akka.streams.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class Notification {

    public final String id;
    public final String body;
}
