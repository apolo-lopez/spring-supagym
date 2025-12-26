package com.codemystack.apps.supagym.models.membership;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "membership_plans")
@Getter
@Setter
public class MembershipPlan extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 3)
    private String currency = "MXN";

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
