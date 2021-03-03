# Kafka Streams Demo App

### Steps to run (cd to project directory):
1. Setup local Kafka cluster in [Docker](https://www.docker.com/get-started)
 
    ```
    docker-compose -f kafka-single-broker.yml up --d
    ```

2. create topics `input-topic-1`, `input-topic-2`, `output-topic-1`

- Open bash shell in the broker container
  ```
  docker exec -it broker bash
  ```
- execute:
  * ```kafka-topics --bootstrap-server localhost:9092 --create --topic input-topic-1 --replication-factor 1 --partitions 1```
  * ```kafka-topics --bootstrap-server localhost:9092 --create --topic input-topic-2 --replication-factor 1 --partitions 1```
  * ```kafka-topics --bootstrap-server localhost:9092 --create --topic output-topic-1 --replication-factor 1 --partitions 1```
  * Ctrl + C to exit the bash shell
  
3. Run as Spring Boot Application

    ``` 
    gradlew bootRun
    ```
