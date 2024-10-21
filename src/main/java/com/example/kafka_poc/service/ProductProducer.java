package com.example.kafka_poc.service;

import com.example.kafka_poc.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductProducer {

    private static final String TOPIC = "productGrp";

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    public void sendProduct(Product product) {
        CompletableFuture<SendResult<String, Product>> future = kafkaTemplate.send(TOPIC, product);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Product sent successfully: " + product);
            } else {
                System.out.println("Failed to send product: " + product);
            }
        });
    }
}
