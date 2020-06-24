package com.kt.kafkaconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.kafkaconsumer.entity.Event;
import com.kt.kafkaconsumer.jpa.EventRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired EventRepo eventRepo;
	
	
	public void processEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		Event event = objectMapper.readValue(consumerRecord.value(), Event.class);
		log.info("event is : {} ",event);
		
		switch (event.getVentType()) {
		case NEW:
			//insert
			save(event);
			break;
		case UPDATE:
			 //update
			save(event);
			break;

		default:
			log.info("Invalid Event Type ");
			break;
		}
		
	}



	private void save(Event event) {
		// TODO Auto-generated method stub
		
		event.getBook().setEvent(event);
		eventRepo.save(event);
		log.info("Sucessfully Persisted {} ",event);
	}
	

}
