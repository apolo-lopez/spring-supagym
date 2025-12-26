package com.codemystack.apps.supagym.repositories.event;

import com.codemystack.apps.supagym.models.event.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, UUID> {
}
