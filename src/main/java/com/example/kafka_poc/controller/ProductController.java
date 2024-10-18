package com.example.kafka_poc.controller;

import com.example.kafka_poc.model.Product;
import com.example.kafka_poc.service.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductProducer productProducer;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            // Send product data directly to Kafka
            productProducer.sendProduct(product);
            return ResponseEntity.ok("Product sent to Kafka.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
