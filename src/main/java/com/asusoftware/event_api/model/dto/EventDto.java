package com.asusoftware.event_api.model.dto;

import com.asusoftware.event_api.model.Event;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventDto {

    private UUID id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @Size(min = 2, max = 255)
    private String location;

    @Size(max = 500)
    private String description;

    public static EventDto toDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setDate(event.getDate());
        eventDto.setLocation(event.getLocation());
        eventDto.setDescription(event.getDescription());
        return eventDto;
    }

    public static Event toEntity(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        return event;
    }
}
