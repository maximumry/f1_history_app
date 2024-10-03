package com.f1.f1history.service.event;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.f1.f1history.dao.EventInfoMapper;
import com.f1.f1history.entity.Event;
import com.f1.f1history.form.EventForm;

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
	public void insertEvent(EventForm form) {
		eventInfoMapper.insertEvent(form);
	}
	
	@Override
	public void updateEvent(String driverId, EventForm form) {
		eventInfoMapper.updateEvent(driverId, form.getDriverId(), form.getDescription(), form.getDate(), form.getCategory());
	}
	
	@Override
	public Optional<Event> getEvent(String driverId){
		return eventInfoMapper.getEvent(driverId);
	}
	
	@Override
	public void deleteEvent(String driverId) {
		eventInfoMapper.deleteEvent(driverId);
	}

}
