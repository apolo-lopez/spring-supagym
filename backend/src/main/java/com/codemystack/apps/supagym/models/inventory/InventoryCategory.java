package com.codemystack.apps.supagym.models.inventory;

import com.codemystack.apps.supagym.models.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "inventory_categories")
@Getter
@Setter
public class InventoryCategory extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "parent_category_id", columnDefinition = "UUID")
    private UUID parentCategoryId;
}
