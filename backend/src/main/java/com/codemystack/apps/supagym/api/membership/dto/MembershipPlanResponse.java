package com.codemystack.apps.supagym.api.membership.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MembershipPlanResponse(
        UUID id,
        String name,
        String description,
        Integer durationDays,
        BigDecimal price,
        String currency,
        String benefits,
        Boolean isActive
) {
}
