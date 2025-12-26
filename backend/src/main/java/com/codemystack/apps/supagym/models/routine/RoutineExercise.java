package com.codemystack.apps.supagym.models.routine;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "routine_exercises")
@Getter
@Setter
public class RoutineExercise extends BaseEntity {
    @Column(name = "routine_id", nullable = false, columnDefinition = "UUID")
    private UUID routineId;

    @Column(name = "exercise_id", nullable = false, columnDefinition = "UUID")
    private UUID exerciseId;

    @Column(name = "day_of_week")
    private Integer dayOfWeek; // 1-7

    @Column(name = "order_index")
    private Integer orderIndex;

    private Integer sets;

    @Column(length = 50)
    private String reps;

    @Column(name = "rest_seconds")
    private Integer restSeconds;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
