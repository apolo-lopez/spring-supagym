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
@Table(name = "staff_attendance")
@Getter
@Setter
public class StaffAttendance extends BaseEntity {
    @Column(name = "staff_id", nullable = false, columnDefinition = "UUID")
    private UUID staffId;

    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(name = "shift_start", nullable = false)
    private LocalDateTime shiftStart;

    @Column(name = "shift_end")
    private LocalDateTime shiftEnd;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "check_in_method", length = 20)
    private String checkInMethod;

    @Column(length = 20)
    private String status = "scheduled";

    @Column(columnDefinition = "TEXT")
    private String notes;
}
