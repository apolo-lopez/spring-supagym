package com.codemystack.apps.supagym.repositories.inventory;

import com.codemystack.apps.supagym.models.inventory.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {
}
