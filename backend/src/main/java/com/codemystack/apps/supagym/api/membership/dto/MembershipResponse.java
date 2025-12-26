package com.codemystack.apps.supagym.api.membership.dto;

import java.time.LocalDate;
import java.util.UUID;

public record MembershipResponse(
        UUID id,
        UUID userId,
        UUID planId,
        UUID branchId,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        Boolean autoRenew
) {
}
