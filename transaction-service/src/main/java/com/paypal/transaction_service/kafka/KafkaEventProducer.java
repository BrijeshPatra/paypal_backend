package com.paypal.transaction_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paypal.transaction_service.entity.Transaction;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class KafkaEventProducer {
    private static final String TOPIC="txn-initiated";

    private final KafkaTemplate<String,Transaction>kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventProducer(KafkaTemplate<String, Transaction>kafkaTemplate, ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
        this.kafkaTemplate=kafkaTemplate;

        //register module to handle java date/time serialization
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    public void sendTransactionEvent(String key,Transaction transaction){
        CompletableFuture<SendResult<String,Transaction>> future=kafkaTemplate.send(TOPIC,key,transaction);

        future.thenAccept(result->{
            RecordMetadata metadata=result.getRecordMetadata();
            System.out.println("Kafka message sent successfully"+metadata.topic()+"Partition"+metadata.partition()+"offset"+metadata.offset());
        }).exceptionally(ex->{
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        });
    }
}
