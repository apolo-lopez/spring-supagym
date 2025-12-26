package com.codemystack.apps.supagym.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<UUID> currentTenantHolder = new ThreadLocal<>();

    private final Map<Object, Object> schemaMap = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return currentTenantHolder.get();
    }

    public void setCurrentTenant(UUID tenantId) {
        currentTenantHolder.set(tenantId);
    }

    public UUID getCurrentTenant() {
        return currentTenantHolder.get();
    }

    public void clearCurrentTenant() {
        currentTenantHolder.remove();
    }

    public void registerSchema(UUID tenantId, String schemaName) {
        schemaMap.put(tenantId, schemaName);
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }
}
