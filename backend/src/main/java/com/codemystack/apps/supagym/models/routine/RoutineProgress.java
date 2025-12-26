package com.codemystack.apps.supagym.models.routine;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "routine_progress")
@Getter
@Setter
public class RoutineProgress extends BaseEntity {
    @Column(name = "user_routine_id", nullable = false, columnDefinition = "UUID")
    private UUID userRoutineId;

    @Column(name = "exercise_id", nullable = false, columnDefinition = "UUID")
    private UUID exerciseId;

    @Column(name = "completed_date", nullable = false)
    private LocalDate completedDate;

    @Column(name = "sets_completed")
    private Integer setsCompleted;

    @Column(name = "reps_completed", columnDefinition = "JSONB")
    private String repsCompleted;

    @Column(name = "weight_used", precision = 6, scale = 2)
    private BigDecimal weightUsed;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
