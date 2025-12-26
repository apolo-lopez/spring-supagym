package com.codemystack.apps.supagym.api.membership.dto;

import java.time.LocalDate;
import java.util.UUID;

public record MembershipCreateRequest(
        UUID userId,
        UUID planId,
        UUID branchId,
        LocalDate startDate
) {
}
