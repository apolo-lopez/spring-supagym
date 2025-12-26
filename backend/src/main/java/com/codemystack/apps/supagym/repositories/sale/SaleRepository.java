package com.codemystack.apps.supagym.repositories.sale;

import com.codemystack.apps.supagym.models.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
