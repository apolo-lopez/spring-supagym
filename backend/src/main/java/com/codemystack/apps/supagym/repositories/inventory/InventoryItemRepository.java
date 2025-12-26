package com.codemystack.apps.supagym.repositories.inventory;

import com.codemystack.apps.supagym.models.inventory.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {
}
