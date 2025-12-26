package com.codemystack.apps.supagym.models.inventory;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
public class InventoryMovement extends BaseEntity {
    @Column(name = "item_id", nullable = false, columnDefinition = "UUID")
    private UUID itemId;

    @Column(name = "movement_type", length = 20)
    private String movementType; // purchase, sale, transfer, adjustment, damage

    private Integer quantity;

    @Column(name = "previous_quantity")
    private Integer previousQuantity;

    @Column(name = "new_quantity")
    private Integer newQuantity;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "from_branch_id", columnDefinition = "UUID")
    private UUID fromBranchId;

    @Column(name = "to_branch_id", columnDefinition = "UUID")
    private UUID toBranchId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "processed_by", columnDefinition = "UUID")
    private UUID processedBy;
}
