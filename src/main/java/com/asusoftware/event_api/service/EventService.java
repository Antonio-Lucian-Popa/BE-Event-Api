package com.asusoftware.event_api.service;

import com.asusoftware.event_api.exception.ResourceNotFoundException;
import com.asusoftware.event_api.model.Event;
import com.asusoftware.event_api.model.Invitation;
import com.asusoftware.event_api.model.InvitationStatus;
import com.asusoftware.event_api.model.dto.EventDto;
import com.asusoftware.event_api.repository.EventRepository;
import com.asusoftware.event_api.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    @Value("${external-link.server-mail-url}")
    private String serverMailUrl;
    private final EventRepository eventRepository;
    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public EventDto getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return EventDto.toDto(event);
    }

    public EventDto createEvent(EventDto eventDTO) {
        Event event = EventDto.toEntity(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return EventDto.toDto(savedEvent);
    }

    public EventDto updateEvent(UUID id, EventDto eventDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        existingEvent.setName(eventDTO.getName());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setDescription(eventDTO.getDescription());
        Event updatedEvent = eventRepository.save(existingEvent);
        return EventDto.toDto(updatedEvent);
    }

    public void deleteEvent(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        eventRepository.delete(event);
    }

    public String inviteUserToEvent(UUID id, String email) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        String eventUrl = "http://yourwebsite.com/events/" + id; // Replace with the actual URL of your event page
        String subject = "You're Invited to " + event.getName();
        String body = "Hi,\n\n"
                + "You're invited to the event \"" + event.getName() + "\"!\n\n"
                + "Event Date: " + event.getDate() + "\n"
                + "Location: " + event.getLocation() + "\n\n"
                + "RSVP by visiting the event page: " + eventUrl + "\n\n"
                + "We hope to see you there!\n\n"
                + "Best regards,\n"
                + "Your Event Team";

        try {
            emailService.sendEmail(email, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return "Failed to send invitation to " + email;
        }

        return "Invitation sent to " + email;
    }

    public List<InvitationDTO> getEventInvitations(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        List<Invitation> invitations = event.getInvitations();
        return invitations.stream()
                .map(invitation -> modelMapper.map(invitation, InvitationDTO.class))
                .collect(Collectors.toList());
    }

    public void acceptInvitation(UUID eventId, UUID invitationId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        Invitation invitation = event.getInvitations().stream()
                .filter(inv -> inv.getId().equals(invitationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + invitationId));

        invitation.setStatus(InvitationStatus.ACCEPTED); // Update the invitation status to ACCEPTED
        invitationRepository.save(invitation);
    }

    @Override
    public void rejectInvitation(UUID eventId, UUID invitationId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        Invitation invitation = event.getInvitations().stream()
                .filter(inv -> inv.getId().equals(invitationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + invitationId));

        invitation.setStatus(InvitationStatus.REJECTED); // Update the invitation status to REJECTED
        invitationRepository.save(invitation);
    }
}