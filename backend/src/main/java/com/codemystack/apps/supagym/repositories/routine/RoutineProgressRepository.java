package com.codemystack.apps.supagym.repositories.routine;

import com.codemystack.apps.supagym.models.routine.RoutineProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineProgressRepository extends JpaRepository<RoutineProgress, UUID> {
}
