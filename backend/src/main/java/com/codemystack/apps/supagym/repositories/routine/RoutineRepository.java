package com.codemystack.apps.supagym.repositories.routine;

import com.codemystack.apps.supagym.models.routine.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineRepository extends JpaRepository<Routine, UUID> {
}
