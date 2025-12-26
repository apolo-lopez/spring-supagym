package com.codemystack.apps.supagym.api.users.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserCreateRequest(
        String email,
        String password,
        String fullName,
        String phone,
        LocalDate dateOfBirth,
        String gender,
        String emergencyContactName,
        String emergencyContactPhone,
        UUID roleId,
        UUID branchId,
        String fcmToken,
        String languagePreference
) {
}
