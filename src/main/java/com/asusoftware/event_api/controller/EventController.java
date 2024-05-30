package com.asusoftware.event_api.controller;

import com.asusoftware.event_api.exception.ResourceNotFoundException;
import com.asusoftware.event_api.model.dto.EventDto;
import com.asusoftware.event_api.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable UUID id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDTO) {
        EventDto createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable UUID id, @RequestBody EventDto eventDTO) {
        EventDto updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<String> inviteToEvent(@PathVariable UUID id, @RequestParam String email) {
        String message = eventService.inviteUserToEvent(id, email);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}/invitations")
    public ResponseEntity<List<InvitationDto>> getEventInvitations(@PathVariable UUID id) {
        List<InvitationDto> invitations = eventService.getEventInvitations(id);
        return ResponseEntity.ok(invitations);
    }

    @PostMapping("/{id}/invitations/{invitationId}/accept")
    public ResponseEntity<Void> acceptInvitation(@PathVariable UUID id, @PathVariable UUID invitationId) {
        eventService.acceptInvitation(id, invitationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/invitations/{invitationId}/reject")
    public ResponseEntity<Void> rejectInvitation(@PathVariable UUID id, @PathVariable UUID invitationId) {
        eventService.rejectInvitation(id, invitationId);
        return ResponseEntity.ok().build();
    }
}
