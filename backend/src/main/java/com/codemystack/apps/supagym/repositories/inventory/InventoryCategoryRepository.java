package com.codemystack.apps.supagym.repositories.inventory;

import com.codemystack.apps.supagym.models.inventory.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, UUID> {
}
