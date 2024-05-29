package com.asusoftware.event_api.service;

import com.asusoftware.event_api.exception.ResourceNotFoundException;
import com.asusoftware.event_api.model.Event;
import com.asusoftware.event_api.model.dto.EventDto;
import com.asusoftware.event_api.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventDto::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EventDto> getEventById(UUID id) {
        return eventRepository.findById(id)
                .map(EventDto::toDto);
    }

    public EventDto createEvent(EventDto eventDTO) {
        Event event = EventDto.toEntity(eventDTO);
        return EventDto.toDto(eventRepository.save(event));
    }

    public EventDto updateEvent(UUID id, EventDto eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id :: " + id));

        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setDescription(eventDTO.getDescription());

        return EventDto.toDto(eventRepository.save(event));
    }

    public void deleteEvent(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id :: " + id));
        eventRepository.delete(event);
    }
}