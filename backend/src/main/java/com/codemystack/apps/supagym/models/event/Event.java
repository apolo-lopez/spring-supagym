package com.codemystack.apps.supagym.models.event;

import com.codemystack.apps.supagym.models.common.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event extends SoftDeletableEntity {
    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_type", length = 50)
    private String eventType;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(length = 255)
    private String location;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "registered_count")
    private Integer registeredCount = 0;

    @Column(name = "created_by", columnDefinition = "UUID")
    private UUID createdBy;
}
