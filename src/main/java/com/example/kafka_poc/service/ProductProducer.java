package com.example.kafka_poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
    public class ProductProducer {

        private static final String TOPIC = "productGrp";

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        public void sendMessage(String message) {
           CompletableFuture<SendResult<String,String>> future = kafkaTemplate.send(TOPIC, message);
           future.whenComplete((result,ex)->{
               if(ex == null)
               {
                   System.out.println("message sent successfully");
               }
               else
               {
                   System.out.println("unable to send the message ");
               }

           });

        }
    }

