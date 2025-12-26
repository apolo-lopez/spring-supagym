package com.codemystack.apps.supagym.repositories.routine;

import com.codemystack.apps.supagym.models.routine.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, UUID> {
    List<RoutineExercise> findByRoutineIdOrderByDayOfWeekAscOrderIndexAsc(UUID routineId);
}
