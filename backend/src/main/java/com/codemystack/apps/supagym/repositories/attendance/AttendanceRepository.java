package com.codemystack.apps.supagym.repositories.attendance;

import com.codemystack.apps.supagym.models.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findFirstByUserIdAndCheckInAfterAndCheckOutIsNullOrderByCheckInDesc(
            UUID userId, LocalDateTime from
    );
    Optional<Attendance> findFirstByUserIdAndCheckInAfterAndCheckOutIsNull(UUID userId, LocalDateTime from);
}
