package com.codemystack.apps.supagym.repositories.notification;

import com.codemystack.apps.supagym.models.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Event, UUID> {
}
