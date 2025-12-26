package com.codemystack.apps.supagym.repositories.membership;

import com.codemystack.apps.supagym.models.membership.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, UUID> {
    List<MembershipPlan> findByIsActiveTrue();
}
