package com.codemystack.apps.supagym.api.attendance.dto;

public record CheckInRequest(
        String method,
        String notes
) {
}
