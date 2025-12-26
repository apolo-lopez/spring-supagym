package com.codemystack.apps.supagym.repositories.branch;

import com.codemystack.apps.supagym.models.branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {
}
