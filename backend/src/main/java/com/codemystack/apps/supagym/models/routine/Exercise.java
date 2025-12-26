package com.codemystack.apps.supagym.models.routine;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exercises")
@Getter
@Setter
public class Exercise extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "muscle_group", length = 100)
    private String muscleGroup;

    @Column(name = "equipment_needed", length = 255)
    private String equipmentNeeded;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;

    @Column(name = "thumbnail_url", columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "demonstration_images", columnDefinition = "JSONB")
    private String demonstrationImages;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(columnDefinition = "TEXT")
    private String tips;
}
