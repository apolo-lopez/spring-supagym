package com.codemystack.apps.supagym.api.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        JwtUserView user
) {
}
