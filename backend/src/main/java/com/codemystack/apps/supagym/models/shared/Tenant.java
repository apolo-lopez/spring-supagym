package com.codemystack.apps.supagym.models.shared;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenants", schema = "shared")
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "schema_name", nullable = false, unique = true)
    private String schemaName;

    @Column(nullable = false, unique = true)
    private String subdomain;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private TenantStatus status;

    @Column(name = "subscription_plan_id", columnDefinition = "UUID")
    private String subscriptionPlanId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "max_users")
    private Integer maxUsers = 500;

    @Column(name = "max_branches")
    private Integer maxBranches = 10;
}
