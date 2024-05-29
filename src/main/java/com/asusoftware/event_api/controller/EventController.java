package com.asusoftware.event_api.controller;

import com.asusoftware.event_api.exception.ResourceNotFoundException;
import com.asusoftware.event_api.model.dto.EventDto;
import com.asusoftware.event_api.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable(value = "id") UUID id) {
        EventDto eventDTO = eventService.getEventById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id :: " + id));
        return ResponseEntity.ok().body(eventDTO);
    }

    @PostMapping
    public EventDto createEvent(@Valid @RequestBody EventDto eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable(value = "id") UUID id, @Valid @RequestBody EventDto eventDTO) {
        EventDto updatedEventDTO = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEventDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(value = "id") UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
