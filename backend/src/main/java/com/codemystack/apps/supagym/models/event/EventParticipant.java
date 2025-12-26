package com.codemystack.apps.supagym.models.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_participants")
@Getter
@Setter
@IdClass(EventParticipant.EventParticipantId.class)
public class EventParticipant {
    @Id
    @Column(name = "event_id", columnDefinition = "UUID")
    private UUID eventId;

    @Id
    @Column(name = "user_id", columnDefinition = "UUID")
    private UUID userId;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(length = 20)
    private String status = "registered";

    @Getter
    @Setter
    public static class EventParticipantId implements Serializable {
        private UUID eventId;
        private UUID userId;
    }
}
