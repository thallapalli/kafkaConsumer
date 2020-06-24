package com.kt.kafkaconsumer.jpa;

import org.springframework.data.repository.CrudRepository;

import com.kt.kafkaconsumer.entity.Event;

public interface EventRepo extends CrudRepository<Event, Integer> {

}
