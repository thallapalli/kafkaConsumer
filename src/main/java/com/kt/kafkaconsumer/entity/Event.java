package com.kt.kafkaconsumer.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity

public class Event {
	@Enumerated(EnumType.STRING)
	private EventType ventType;
	@Id
	@GeneratedValue
	private Integer eventId;
	@NotNull
	@OneToOne(mappedBy = "event" , cascade = {CascadeType.ALL} )
	@ToString.Exclude
	private Book book;

}
