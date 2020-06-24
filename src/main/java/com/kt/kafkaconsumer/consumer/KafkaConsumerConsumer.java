package com.kt.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumerConsumer {
	@KafkaListener(topics = {"event"})
	 public void onMessage(ConsumerRecord<Integer,String> consumerRecord) {
		 log.info("consumerRecord-->"+consumerRecord);
	 }
}
