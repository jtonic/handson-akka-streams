package ro.jtonic.handson.akka.streams.consumer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import ro.jtonic.handson.akka.streams.common.conf.AkkaConf;

@SpringBootApplication(
  scanBasePackageClasses = {
      KafkaConsumerMain.class,
      AkkaConf.class
  }
)
public class KafkaConsumerMain {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(KafkaConsumerMain.class)
        .web(WebApplicationType.NONE)
        .bannerMode(Mode.OFF)
        .build().run(args);
  }

  @Bean
  public ConsumerWorker consumerWorker() {
    return new ConsumerWorker();
  }
}
