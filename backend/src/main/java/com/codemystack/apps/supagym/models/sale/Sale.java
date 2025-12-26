package com.codemystack.apps.supagym.models.sale;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sale extends BaseEntity {
    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(name = "customer_id", columnDefinition = "UUID")
    private UUID customerId; // puede ser null si no tiene membresía

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(length = 20)
    private String status = "completed";

    @Column(name = "sold_by", columnDefinition = "UUID")
    private UUID soldBy;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
