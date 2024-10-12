package com.f1.f1history.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.f1.f1history.entity.Event;

@Mapper
public interface EventInfoMapper {
	
	List<Event> getEventAll();
	
	void insertEvent(Event event);
	
	void updateEvent(@Param("event") Event event);
	
	Optional<Event> getEvent(String eventId);
	
	void deleteEvent(String eventId);

}
