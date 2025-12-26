package com.codemystack.apps.supagym.models.attendance;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance")
@Getter
@Setter
public class Attendance extends BaseEntity {
    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn = LocalDateTime.now();

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "check_in_method", length = 20)
    private String checkInMethod;

    @Column(name = "check_out_method", length = 20)
    private String checkOutMethod;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
