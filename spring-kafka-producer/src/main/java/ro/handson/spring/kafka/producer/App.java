package ro.handson.spring.kafka.producer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .sources(App.class)
                .run(args);
    }

    private final ApplicationContext ctx;
    private final Producer producer;
    private final Consumer consumer;
    private final int messagesNo;

    public App(
            ApplicationContext ctx,
            Producer producer,
            Consumer consumer,
            @Value("${messages_no}") int messagesNo) {

        this.producer = producer;
        this.ctx = ctx;
        this.consumer = consumer;
        this.messagesNo = messagesNo;
    }

    @SneakyThrows
    @Override
    public void run(String... args) {
        IntStream
                .rangeClosed(1, messagesNo)
                .forEach(
                        i -> producer.sendMessage(String.valueOf(i))
                );

        consumer.latch.await(10, TimeUnit.SECONDS);
        shutdown();
    }

    private void shutdown() {
        SpringApplication.exit(ctx, () -> 0);
    }
}
