package ro.jtonic.handson.akka.streams.conf;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Source.class)
public class AkkaConf {

  @Bean
  @ConditionalOnMissingBean(ActorSystem.class)
  public ActorSystem actorSystem() {
    return ActorSystem.create("e-core");
  }

  @Bean
  @ConditionalOnMissingBean(Materializer.class)
  public Materializer materializer(ActorSystem actorSystem) {
    return Materializer.createMaterializer(actorSystem);
  }
}
