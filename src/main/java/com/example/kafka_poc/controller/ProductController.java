package com.example.kafka_poc.controller;

import com.example.kafka_poc.model.Product;
import com.example.kafka_poc.service.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductProducer productProducer;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(@RequestPart("file") MultipartFile file) {
        try {
            // Parse the CSV file
            List<Product> products = parseCsvFile(file);

            for (Product product : products) {
                productProducer.sendProduct(product);
            }

            return ResponseEntity.ok("Products sent to Kafka.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    private List<Product> parseCsvFile(MultipartFile file) throws Exception {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser.getRecords()) {
                Product product = new Product();
                product.setName(csvRecord.get("name"));
                product.setDescription(csvRecord.get("description"));
                product.setPrice(Double.parseDouble(csvRecord.get("price")));

                products.add(product);
            }
        }
        return products;
    }
}
