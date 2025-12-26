package com.codemystack.apps.supagym.api.users.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserUpdateRequest(
        String fullName,
        String phone,
        LocalDate dateOfBirth,
        String gender,
        String emergencyContactName,
        String emergencyContactPhone,
        UUID roleId,
        UUID branchId,
        String fcmToken,
        String languagePreference,
        Boolean isActive
) {
}
