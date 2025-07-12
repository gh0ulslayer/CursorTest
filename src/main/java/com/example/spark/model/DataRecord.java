package com.example.spark.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DataRecord implements Serializable {
    
    private String id;
    private String name;
    private Double value;
    private LocalDateTime timestamp;
    private String category;

    // Default constructor
    public DataRecord() {}

    // Constructor with all fields
    public DataRecord(String id, String name, Double value, LocalDateTime timestamp, String category) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
        this.category = category;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRecord that = (DataRecord) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 