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
@Table(name = "sale_items")
@Getter
@Setter
public class SaleItem extends BaseEntity {
    @Column(name = "sale_id", nullable = false, columnDefinition = "UUID")
    private UUID saleId;

    @Column(name = "item_id", nullable = false, columnDefinition = "UUID")
    private UUID itemId;

    private Integer quantity;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;
}
