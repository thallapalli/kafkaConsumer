package com.kt.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KafkaConsumerApplication {

	public static void main(String[] args) {
		log.info("Hello In main");
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
