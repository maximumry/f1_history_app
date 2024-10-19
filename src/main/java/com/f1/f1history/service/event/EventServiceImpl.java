package com.f1.f1history.service.event;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.f1.f1history.dao.EventInfoMapper;
import com.f1.f1history.entity.Event;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
	
	private final EventInfoMapper eventInfoMapper;

	@Override
	public List<Event> getEventAll() {
		return eventInfoMapper.getEventAll();
	}
	
	@Override
	public void insertEvent(Event event) {
		eventInfoMapper.insertEvent(event);
	}
	
	@Override
	public void updateEvent(Event event) {
		eventInfoMapper.updateEvent(event);
	}
	
	@Override
	public Optional<Event> getEvent(String eventId){
		return eventInfoMapper.getEvent(eventId);
	}
	
	@Override
	public void deleteEvent(Event event) {
		eventInfoMapper.deleteEvent(event);
	}

}
