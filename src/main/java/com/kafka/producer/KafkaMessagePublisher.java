package com.kafka.producer;

import com.kafka.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    @Value("${app.topic.name}")
    private String topicName;


    public void sendEvents(User  user){
        try{
            CompletableFuture<SendResult<String, Object>> completableFuture = template.send(topicName,user);
            completableFuture.whenComplete((result,ex)->{
                if (ex == null) {
                    System.out.println("Sent message=[" + user.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            user.toString() + "] due to : " + ex.getMessage());
                }
            });
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
