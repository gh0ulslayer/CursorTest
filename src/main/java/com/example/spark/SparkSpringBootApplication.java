package com.example.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SparkSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkSpringBootApplication.class, args);
    }

    @Configuration
    public static class SparkConfig {

        @Bean
        public SparkConf sparkConf() {
            return new SparkConf()
                    .setAppName("Spark Spring Boot Application")
                    .setMaster("local[*]")
                    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                    .set("spark.sql.adaptive.enabled", "true")
                    .set("spark.sql.adaptive.coalescePartitions.enabled", "true");
        }

        @Bean
        public JavaSparkContext javaSparkContext(SparkConf sparkConf) {
            return new JavaSparkContext(sparkConf);
        }

        @Bean
        public SparkSession sparkSession(JavaSparkContext javaSparkContext) {
            return SparkSession.builder()
                    .sparkContext(javaSparkContext.sc())
                    .appName("Spark Spring Boot Application")
                    .config("spark.sql.warehouse.dir", "spark-warehouse")
                    .enableHiveSupport()
                    .getOrCreate();
        }
    }
} 