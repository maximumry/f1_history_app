package com.f1.f1history.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.f1.f1history.entity.Event;
import com.f1.f1history.form.EventForm;

@Mapper
public interface EventInfoMapper {
	
	List<Event> getEventAll();
	
	void insertEvent(EventForm form);
	
	void updateEvent(@Param("eventId") String eventId,
			@Param("driverId") String driverId,
			@Param("description") String description,
			@Param("date") LocalDate date,
			@Param("category") String category);
	
	Optional<Event> getEvent(String eventId);
	
	void deleteEvent(String eventId);

}
