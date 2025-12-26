package com.codemystack.apps.supagym.repositories.user;

import com.codemystack.apps.supagym.models.user.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermission.RolePermissionId> {
}
