package com.example.multiplestream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanCustomizer;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class MultipleStreamApplication {

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> processXXX() {
        return input ->
                input.peek((key, value) -> log.info("Received-XXX: key:{}, value:{}", key, value))
                        .mapValues((ValueMapper<String, String>) String::toUpperCase)
                        .peek((key, value) -> log.info("Output-XXX: key:{}, value:{}", key, value));
    }

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> processYYY() {
        return input ->
                input.peek((key, value) -> log.info("Received-YYY key:{}, value:{}", key, value))
                        .mapValues((ValueMapper<String, String>) String::toUpperCase)
                        .peek((key, value) -> log.info("Output-YYY key:{}, value:{}", key, value));
    }

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> processZZZ() {
        return input ->
                input.peek((key, value) -> log.info("Received-ZZZ key:{}, value:{}", key, value))
                        .mapValues((ValueMapper<String, String>) String::toUpperCase)
                        .peek((key, value) -> log.info("Output-ZZZ key:{}, value:{}", key, value));
    }


    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer() {
        return factoryBean ->
                factoryBean.setKafkaStreamsCustomizer(
                        kafkaStreams -> kafkaStreams.setUncaughtExceptionHandler(
                                (t, e) -> {
                                    log.error("Error occurred while Processing, shutting down: ", e);
                                    System.exit(0);
                                }));
    }

    public static void main(String[] args) {
        SpringApplication.run(MultipleStreamApplication.class, args);
    }

}
