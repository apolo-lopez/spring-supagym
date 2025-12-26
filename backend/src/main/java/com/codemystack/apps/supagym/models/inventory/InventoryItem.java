package com.codemystack.apps.supagym.models.inventory;

import com.codemystack.apps.supagym.models.common.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
public class InventoryItem extends SoftDeletableEntity {
    @Column(name = "category_id", columnDefinition = "UUID")
    private UUID categoryId;

    @Column(name = "branch_id", columnDefinition = "UUID")
    private UUID branchId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "item_type", length = 50)
    private String itemType; // equipment, supplement, merchandise

    @Column(length = 100, unique = true)
    private String sku;

    private Integer quantity = 0;

    @Column(name = "min_stock_level")
    private Integer minStockLevel;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(length = 255)
    private String supplier;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "warranty_expiry")
    private LocalDate warrantyExpiry;

    @Column(length = 50)
    private String condition; // new, good, needsrepair, damaged

    @Column(length = 255)
    private String location;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "is_sellable")
    private Boolean isSellable = false;
}
