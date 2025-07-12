package com.example.spark.controller;

import com.example.spark.service.SparkService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spark")
@CrossOrigin(origins = "*")
public class SparkController {

    @Autowired
    private SparkService sparkService;

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Spark Spring Boot"));
    }

    /**
     * Get Spark application information
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getApplicationInfo() {
        return ResponseEntity.ok(sparkService.getApplicationInfo());
    }

    /**
     * Perform word count on provided text
     */
    @PostMapping("/wordcount")
    public ResponseEntity<Map<String, Long>> wordCount(@RequestBody List<String> textLines) {
        JavaRDD<String> textRDD = sparkService.createStringRDD(textLines);
        Map<String, Long> result = sparkService.wordCount(textRDD);
        return ResponseEntity.ok(result);
    }

    /**
     * Read CSV file
     */
    @GetMapping("/read-csv")
    public ResponseEntity<String> readCSV(@RequestParam String path) {
        try {
            Dataset<Row> dataset = sparkService.readCSV(path);
            long count = dataset.count();
            return ResponseEntity.ok("CSV file read successfully. Row count: " + count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error reading CSV: " + e.getMessage());
        }
    }

    /**
     * Read JSON file
     */
    @GetMapping("/read-json")
    public ResponseEntity<String> readJSON(@RequestParam String path) {
        try {
            Dataset<Row> dataset = sparkService.readJSON(path);
            long count = dataset.count();
            return ResponseEntity.ok("JSON file read successfully. Row count: " + count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error reading JSON: " + e.getMessage());
        }
    }

    /**
     * Process integer data
     */
    @PostMapping("/process-numbers")
    public ResponseEntity<Map<String, Object>> processNumbers(@RequestBody List<Integer> numbers) {
        JavaRDD<Integer> numberRDD = sparkService.createIntegerRDD(numbers);
        
        double sum = numberRDD.reduce((a, b) -> a + b);
        double average = sum / numbers.size();
        long count = numberRDD.count();
        
        Map<String, Object> result = Map.of(
                "sum", sum,
                "average", average,
                "count", count,
                "min", numberRDD.min(),
                "max", numberRDD.max()
        );
        
        return ResponseEntity.ok(result);
    }

    /**
     * Stop Spark application
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stop() {
        sparkService.stop();
        return ResponseEntity.ok("Spark application stopped successfully");
    }
} 