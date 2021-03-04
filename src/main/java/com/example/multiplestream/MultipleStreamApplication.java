package com.example.multiplestream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanCustomizer;

import java.util.function.BiFunction;

@Slf4j
@SpringBootApplication
public class MultipleStreamApplication {

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, String>[]> process() {
        Predicate<String, String> toOutputTopic1 = (k, v) -> k.equalsIgnoreCase("app1");
        Predicate<String, String> toOutputTopic2 = (k, v) -> k.equalsIgnoreCase("app2");
        return (input1, input2) ->
                input1.merge(input2)
                        .peek((key, value) -> log.info("Received key:{}, value:{}", key, value))
                        .map((key, value) -> new KeyValue<>(key.toUpperCase(), "processed value:" + value))
                        .peek((key, value) -> log.info("Output key:{}, value:{}", key, value))
                        .branch(toOutputTopic1, toOutputTopic2);
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

    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer() {
        return factoryBean ->
                factoryBean.setKafkaStreamsCustomizer(
                        kafkaStreams -> {
                            kafkaStreams.setUncaughtExceptionHandler(
                                    (t, e) -> {
                                        log.error("Error occurred while Processing, shutting down: ", e);
                                        System.exit(0);
                                    });
                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(MultipleStreamApplication.class, args);
    }

}
