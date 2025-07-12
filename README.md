# Spark Spring Boot Application

A comprehensive boilerplate for developing Apache Spark applications using Java Spring Boot. This project provides a foundation for building scalable data processing applications with REST API endpoints.

## Features

- **Spring Boot 3.2.0** with Java 17
- **Apache Spark 3.5.0** integration
- **REST API** endpoints for Spark operations
- **H2 Database** for development
- **Actuator** for monitoring and health checks
- **Comprehensive testing** framework
- **Maven** build system

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- At least 4GB RAM (recommended for Spark operations)

## Project Structure

```
src/
├── main/
│   ├── java/com/example/spark/
│   │   ├── SparkSpringBootApplication.java    # Main application class
│   │   ├── controller/
│   │   │   └── SparkController.java          # REST API endpoints
│   │   ├── service/
│   │   │   └── SparkService.java             # Spark operations service
│   │   └── model/
│   │       └── DataRecord.java               # Data model
│   └── resources/
│       └── application.yml                   # Configuration
└── test/
    └── java/com/example/spark/
        └── SparkServiceTest.java             # Unit tests
```

## Quick Start

### 1. Build the Project

```bash
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Verify the Application

Check the health endpoint:
```bash
curl http://localhost:8080/api/spark/health
```

## API Endpoints

### Health Check
- **GET** `/api/spark/health`
- Returns application status

### Application Info
- **GET** `/api/spark/info`
- Returns Spark application information

### Word Count
- **POST** `/api/spark/wordcount`
- Body: `["Hello world", "Hello spark", "Hello spring boot"]`
- Returns word frequency map

### Number Processing
- **POST** `/api/spark/process-numbers`
- Body: `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`
- Returns statistics (sum, average, count, min, max)

### File Operations
- **GET** `/api/spark/read-csv?path=file:///path/to/file.csv`
- **GET** `/api/spark/read-json?path=file:///path/to/file.json`

### Stop Application
- **POST** `/api/spark/stop`
- Gracefully stops Spark context

## Example Usage

### Word Count Example

```bash
curl -X POST http://localhost:8080/api/spark/wordcount \
  -H "Content-Type: application/json" \
  -d '["Hello world", "Hello spark", "Hello spring boot", "Apache spark is awesome"]'
```

Response:
```json
{
  "Hello": 3,
  "world": 1,
  "spark": 2,
  "spring": 1,
  "boot": 1,
  "Apache": 1,
  "is": 1,
  "awesome": 1
}
```

### Number Processing Example

```bash
curl -X POST http://localhost:8080/api/spark/process-numbers \
  -H "Content-Type: application/json" \
  -d '[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]'
```

Response:
```json
{
  "sum": 55.0,
  "average": 5.5,
  "count": 10,
  "min": 1,
  "max": 10
}
```

## Configuration

The application uses `application.yml` for configuration. Key settings:

- **Server Port**: 8080
- **Spark Master**: local[*] (for development)
- **Database**: H2 in-memory
- **Logging**: DEBUG level for application classes

## Development

### Running Tests

```bash
mvn test
```

### Building JAR

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

### IDE Setup

1. Import as Maven project
2. Ensure Java 17 is configured
3. Run `SparkSpringBootApplication` as Spring Boot application

## Spark Configuration

The application is configured for local development with:

- **Master**: `local[*]` (uses all available cores)
- **Serializer**: KryoSerializer for better performance
- **Adaptive Query Execution**: Enabled
- **Hive Support**: Enabled

For production deployment, modify the Spark configuration in `SparkSpringBootApplication.java`.

## Monitoring

### Actuator Endpoints

- Health: `http://localhost:8080/actuator/health`
- Info: `http://localhost:8080/actuator/info`
- Metrics: `http://localhost:8080/actuator/metrics`

### H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Production Considerations

1. **Spark Configuration**: Update master URL for cluster deployment
2. **Memory Settings**: Configure appropriate memory settings for your data size
3. **Security**: Implement proper authentication and authorization
4. **Monitoring**: Add comprehensive logging and monitoring
5. **Error Handling**: Implement proper error handling and recovery mechanisms

## Troubleshooting

### Common Issues

1. **OutOfMemoryError**: Increase JVM heap size
   ```bash
   mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx4g"
   ```

2. **Port Already in Use**: Change server port in `application.yml`

3. **Spark Context Issues**: Check if Spark is properly initialized in logs

### Logs

Check application logs for detailed information about Spark operations and any errors.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.
