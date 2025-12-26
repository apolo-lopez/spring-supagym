package com.codemystack.apps.supagym.repositories.routine;

import com.codemystack.apps.supagym.models.routine.UserRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoutineRepository extends JpaRepository<UserRoutine, UUID> {
    List<UserRoutine> findByUserIdAndStatus(UUID userId, String status);
}
