package com.codemystack.apps.supagym.api.auth.dto;

public record AuthRequest(
        String email,
        String password,
        String tenantId,      // opcional, si no usas subdominio
        String tenantSubdomain // opcional
) {}
