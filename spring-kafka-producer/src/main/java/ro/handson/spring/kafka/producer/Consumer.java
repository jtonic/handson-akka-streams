package ro.handson.spring.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class Consumer {

    private final String groupId;
    final CountDownLatch latch;

    public Consumer(
            @Value("${kafka.group_id}") String groupId,
            @Value("${messages_no}") int messagesNo
            ) {

        this.groupId = groupId;
        this.latch = new CountDownLatch(messagesNo);
    }

    @KafkaListener(topics = "${kafka.topic_name}", groupId = "${kafka.group_id}")
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.printf("Received message '%s' in group %s from partition '%d'%n", message, groupId , partition);
        latch.countDown();
    }
}
