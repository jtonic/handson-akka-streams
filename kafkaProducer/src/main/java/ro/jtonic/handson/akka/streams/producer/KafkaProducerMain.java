package ro.jtonic.handson.akka.streams.producer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ro.jtonic.handson.akka.streams.common.conf.AkkaConf;

@SpringBootApplication(
    scanBasePackageClasses = {
        KafkaProducerMain.class,
        AkkaConf.class
    }
)
public class KafkaProducerMain {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(KafkaProducerMain.class)
        .web(WebApplicationType.NONE)
        .bannerMode(Mode.OFF)
        .build().run(args);
  }
}
