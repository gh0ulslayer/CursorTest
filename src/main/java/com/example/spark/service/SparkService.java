package com.example.spark.service;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SparkService {

    @Autowired
    private JavaSparkContext javaSparkContext;

    @Autowired
    private SparkSession sparkSession;

    /**
     * Create a simple RDD from a list of strings
     */
    public JavaRDD<String> createStringRDD(List<String> data) {
        return javaSparkContext.parallelize(data);
    }

    /**
     * Create a simple RDD from a list of integers
     */
    public JavaRDD<Integer> createIntegerRDD(List<Integer> data) {
        return javaSparkContext.parallelize(data);
    }

    /**
     * Read a CSV file and return as Dataset
     */
    public Dataset<Row> readCSV(String path) {
        return sparkSession.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv(path);
    }

    /**
     * Read a JSON file and return as Dataset
     */
    public Dataset<Row> readJSON(String path) {
        return sparkSession.read().json(path);
    }

    /**
     * Write Dataset to CSV file
     */
    public void writeCSV(Dataset<Row> dataset, String path) {
        dataset.write()
                .option("header", "true")
                .mode("overwrite")
                .csv(path);
    }

    /**
     * Write Dataset to JSON file
     */
    public void writeJSON(Dataset<Row> dataset, String path) {
        dataset.write()
                .mode("overwrite")
                .json(path);
    }

    /**
     * Perform word count on a text RDD
     */
    public Map<String, Long> wordCount(JavaRDD<String> textRDD) {
        return textRDD
                .flatMap(line -> java.util.Arrays.asList(line.split(" ")).iterator())
                .mapToPair(word -> new org.apache.spark.api.java.Tuple2<>(word, 1L))
                .reduceByKey((a, b) -> a + b)
                .collectAsMap();
    }

    /**
     * Get Spark application info
     */
    public Map<String, String> getApplicationInfo() {
        return Map.of(
                "appName", sparkSession.conf().get("spark.app.name"),
                "master", sparkSession.conf().get("spark.master"),
                "version", sparkSession.version()
        );
    }

    /**
     * Stop Spark context and session
     */
    public void stop() {
        if (sparkSession != null) {
            sparkSession.stop();
        }
        if (javaSparkContext != null) {
            javaSparkContext.stop();
        }
    }
} 