package com.kt.kafkaconsumer.intg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kt.kafkaconsumer.consumer.KafkaConsumerConsumer;
import com.kt.kafkaconsumer.entity.Event;
import com.kt.kafkaconsumer.jpa.EventRepo;
import com.kt.kafkaconsumer.service.EventService;

@SpringBootTest
@EmbeddedKafka(topics = { "event" }, partitions = 1)
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
		"spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}",
		"spring.kafka.consumer.group-id=EmbeddedKafkaTest" 
		})
public class KafkaConsumerConsumerTest {
	@Autowired
	EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	KafkaListenerEndpointRegistry endpointRegistry;

	@SpyBean
	KafkaConsumerConsumer kafkaConsumerConsumer;

	@SpyBean
	EventService eventService;

	@Autowired
	EventRepo eventRepo;

	@BeforeEach
	void setUp() {

		for (MessageListenerContainer messageListenerContainer : endpointRegistry.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
		}
	}

	@AfterEach
	void tearDown() {
		eventRepo.deleteAll();
	}

	@Test
	void publishNewLibraryEvent() throws ExecutionException, InterruptedException, JsonProcessingException {
		// given
		String json = " {\"eventId\":null,\"ventType\":\"NEW\",\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot\",\"bookAuthor\":\"Dilip\"}}";
		
		kafkaTemplate.setDefaultTopic("event");
		kafkaTemplate.sendDefault(json).get();

		// when
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(3, TimeUnit.SECONDS);

		// then
		verify(kafkaConsumerConsumer, times(1)).onMessage(isA(ConsumerRecord.class));
		verify(eventService, times(1)).processEvent((isA(ConsumerRecord.class)));

		List<Event> libraryEventList = (List<Event>) eventRepo.findAll();
		assert libraryEventList.size() == 1;
		libraryEventList.forEach(libraryEvent -> {
			assert libraryEvent.getEventId() != null;
			assertEquals(456, libraryEvent.getBook().getBookId());
		});

	}
}