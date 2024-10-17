package com.example.kafka_poc.service;

import com.example.kafka_poc.model.Product;
import com.example.kafka_poc.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    @Autowired
    private ProductRepository productRepository;


}