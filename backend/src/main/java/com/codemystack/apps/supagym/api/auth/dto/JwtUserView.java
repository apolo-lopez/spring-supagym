package com.codemystack.apps.supagym.api.auth.dto;

import java.util.UUID;

public record JwtUserView(
        UUID id,
        String email,
        String fullName,
        String role
) {
}
