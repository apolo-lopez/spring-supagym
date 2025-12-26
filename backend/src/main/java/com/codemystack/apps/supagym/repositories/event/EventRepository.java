package com.codemystack.apps.supagym.repositories.event;

import com.codemystack.apps.supagym.models.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
