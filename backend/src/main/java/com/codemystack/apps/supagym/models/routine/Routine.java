package com.codemystack.apps.supagym.models.routine;

import com.codemystack.apps.supagym.models.common.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "routines")
@Getter
@Setter
public class Routine extends SoftDeletableEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel; // beginner, intermediate, advanced

    @Column(length = 100)
    private String goal; // weightloss, musclegain, endurance...

    @Column(name = "duration_weeks")
    private Integer durationWeeks;

    @Column(name = "created_by", columnDefinition = "UUID")
    private UUID createdBy;

    @Column(name = "is_template")
    private Boolean isTemplate = false;
}
