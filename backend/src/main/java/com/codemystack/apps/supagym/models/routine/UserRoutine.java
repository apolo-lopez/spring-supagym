package com.codemystack.apps.supagym.models.routine;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_routines")
@Getter
@Setter
public class UserRoutine extends BaseEntity {
    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(name = "routine_id", nullable = false, columnDefinition = "UUID")
    private UUID routineId;

    @Column(name = "assigned_by", columnDefinition = "UUID")
    private UUID assignedBy;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 20)
    private String status = "active"; // active, completed, cancelled

    @Column(name = "progress_notes", columnDefinition = "TEXT")
    private String progressNotes;
}
