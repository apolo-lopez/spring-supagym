package com.codemystack.apps.supagym.api.attendance.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AttendanceResponse(
        UUID id,
        UUID userId,
        UUID branchId,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        String checkInMethod,
        String checkOutMethod,
        String notes
) {
}
