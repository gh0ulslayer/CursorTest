package com.example.spark;

import com.example.spark.service.SparkService;
import org.apache.spark.api.java.JavaRDD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SparkServiceTest {

    @Autowired
    private SparkService sparkService;

    @Test
    public void testWordCount() {
        List<String> textLines = Arrays.asList(
                "Hello world",
                "Hello spark",
                "Hello spring boot",
                "Apache spark is awesome"
        );

        JavaRDD<String> textRDD = sparkService.createStringRDD(textLines);
        Map<String, Long> wordCount = sparkService.wordCount(textRDD);

        assertNotNull(wordCount);
        assertEquals(2L, wordCount.get("Hello").longValue());
        assertEquals(2L, wordCount.get("spark").longValue());
        assertEquals(1L, wordCount.get("world").longValue());
    }

    @Test
    public void testNumberProcessing() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> numberRDD = sparkService.createIntegerRDD(numbers);

        double sum = numberRDD.reduce((a, b) -> a + b);
        double average = sum / numbers.size();
        long count = numberRDD.count();

        assertEquals(55.0, sum);
        assertEquals(10L, count);
        assertEquals(5.5, average);
    }

    @Test
    public void testApplicationInfo() {
        Map<String, String> info = sparkService.getApplicationInfo();
        
        assertNotNull(info);
        assertTrue(info.containsKey("appName"));
        assertTrue(info.containsKey("master"));
        assertTrue(info.containsKey("version"));
    }
} 