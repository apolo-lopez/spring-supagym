package com.codemystack.apps.supagym.api.membership.dto;

import java.math.BigDecimal;

public record MembershipPlanCreateRequest(
        String name,
        String description,
        Integer durationDays,
        BigDecimal price,
        String currency,
        String benefits
) {
}
