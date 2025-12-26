package com.codemystack.apps.supagym.repositories.tenant;

import com.codemystack.apps.supagym.models.shared.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByIdAndStatus_Code(UUID tenantId, String code);
    Optional<Tenant> findBySubdomainAndStatus_Code(String subdomain, String code);
}
