package com.codemystack.apps.supagym.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "role_permissions")
@Getter
@Setter
@IdClass(RolePermission.RolePermissionId.class)
public class RolePermission {
    @Id
    @Column(name = "role_id", columnDefinition = "UUID")
    private UUID roleId;

    @Id
    @Column(name = "permission_id", columnDefinition = "UUID")
    private UUID permissionId;

    @Getter
    @Setter
    public static class RolePermissionId implements Serializable {
        private UUID roleId;
        private UUID permissionId;
    }
}
