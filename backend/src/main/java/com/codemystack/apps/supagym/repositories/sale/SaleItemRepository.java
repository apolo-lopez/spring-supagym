package com.codemystack.apps.supagym.repositories.sale;

import com.codemystack.apps.supagym.models.sale.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {
}
