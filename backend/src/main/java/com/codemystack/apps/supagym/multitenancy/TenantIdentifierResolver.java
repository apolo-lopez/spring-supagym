package com.codemystack.apps.supagym.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component("currentTenantIdentifierResolver")
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private static final ThreadLocal<String> currentTenantHolder = new ThreadLocal<>();

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = currentTenantHolder.get();
        if (tenantId == null) {
            return "public";
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    public void setCurrentTenant(String schema) {
        currentTenantHolder.set(schema);
    }

    public void clearCurrentTenant() {
        currentTenantHolder.remove();
    }
}
