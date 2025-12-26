package com.codemystack.apps.supagym.repositories.routine;

import com.codemystack.apps.supagym.models.routine.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
}
