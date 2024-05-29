package com.asusoftware.event_api.repository;

import com.asusoftware.event_api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
