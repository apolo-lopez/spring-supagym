package com.codemystack.apps.supagym.repositories.attendance;

import com.codemystack.apps.supagym.models.attendance.StaffAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffAttendanceRepository extends JpaRepository<StaffAttendance, UUID> {
}
