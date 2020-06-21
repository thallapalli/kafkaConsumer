package com.kt.kafkaconsumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class KafkaConsumerController {
	@GetMapping(value = "/v1/sayhello")
	public String sayHello() {
		log.info("Hello in controller");
		return "Hello from Consumer App";
	}
	
}
