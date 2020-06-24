package com.kt.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kt.kafkaconsumer.service.EventService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumerConsumer {
	@Autowired 
	private EventService eventService;
	@KafkaListener(topics = {"event"})
	 public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		 log.info("consumerRecord-->"+consumerRecord);
		 eventService.processEvent(consumerRecord);
		 
	 }
}
