package com.codemystack.apps.supagym.models.membership;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "memberships")
@Getter
@Setter
public class Membership extends BaseEntity {
    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(name = "plan_id", nullable = false, columnDefinition = "UUID")
    private UUID planId;

    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(length = 20)
    private String status = "active";

    @Column(name = "auto_renew")
    private Boolean autoRenew = false;
}
