package ro.jtonic.handson.akka.streams.consumer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import ro.jtonic.handson.akka.streams.common.conf.AkkaConf;

@SpringBootApplication(
  scanBasePackageClasses = {
      App.class,
      AkkaConf.class
  }
)
public class App {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(App.class)
        .web(WebApplicationType.NONE)
        .bannerMode(Mode.OFF)
        .build().run(args);
  }

  @Bean
  public Worker consumerWorker() {
    return new Worker();
  }
}
