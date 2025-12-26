package com.codemystack.apps.supagym.api.attendance.dto;

public record CheckOutRequest(
        String method,
        String notes
) {
}
