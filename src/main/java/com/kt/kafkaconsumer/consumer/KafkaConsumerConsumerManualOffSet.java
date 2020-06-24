package com.kt.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class KafkaConsumerConsumerManualOffSet implements AcknowledgingMessageListener<Integer, String>{
	

	@Override
	@KafkaListener(topics= {"event"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
		// TODO Auto-generated method stub
		 log.info("consumerRecord-->"+consumerRecord);
		 acknowledgment.acknowledge();
	}
}
