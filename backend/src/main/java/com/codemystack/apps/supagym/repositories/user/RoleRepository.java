package com.codemystack.apps.supagym.repositories.user;

import com.codemystack.apps.supagym.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
