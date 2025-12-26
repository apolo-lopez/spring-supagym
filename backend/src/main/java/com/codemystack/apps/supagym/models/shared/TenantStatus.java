package com.codemystack.apps.supagym.models.shared;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenant_statuses", schema = "shared")
@Data
public class TenantStatus {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // active, suspended, cancelled

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
