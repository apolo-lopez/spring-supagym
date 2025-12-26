package com.codemystack.apps.supagym.api.membership.service;

import com.codemystack.apps.supagym.api.membership.dto.MembershipCreateRequest;
import com.codemystack.apps.supagym.api.membership.dto.MembershipPlanCreateRequest;
import com.codemystack.apps.supagym.api.membership.dto.MembershipPlanResponse;
import com.codemystack.apps.supagym.api.membership.dto.MembershipResponse;
import com.codemystack.apps.supagym.models.membership.Membership;
import com.codemystack.apps.supagym.models.membership.MembershipPlan;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.repositories.membership.MembershipPlanRepository;
import com.codemystack.apps.supagym.repositories.membership.MembershipRepository;
import com.codemystack.apps.supagym.repositories.user.UserRepository;
import com.codemystack.apps.supagym.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipService {
    private final MembershipPlanRepository planRepository;
    private  final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public MembershipService(
            MembershipPlanRepository planRepository,
            MembershipRepository membershipRepository,
            UserRepository userRepository
    ) {
        this.planRepository = planRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    public List<MembershipPlanResponse> listActivePlans() {
        return planRepository.findByIsActiveTrue().stream()
                .map(this::toPlanResponse)
                .toList();
    }

    public MembershipPlanResponse createPlan(MembershipPlanCreateRequest request) {
        MembershipPlan plan = new MembershipPlan();
        plan.setName(request.name());
        plan.setDescription(request.description());
        plan.setDurationDays(request.durationDays());
        plan.setPrice(request.price());
        plan.setCurrency(request.currency() != null ? request.currency() : "MXN");
        plan.setBenefits(request.benefits());
        plan.setIsActive(true);

        planRepository.save(plan);
        return toPlanResponse(plan);
    }

    public MembershipResponse createMembership(MembershipCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MembershipPlan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        LocalDate start = request.startDate() != null ? request.startDate() : LocalDate.now();
        LocalDate end = start.plusDays(plan.getDurationDays());

        Membership  membership = new Membership();
        membership.setUserId(user.getId());
        membership.setPlanId(plan.getId());
        membership.setBranchId(request.branchId());
        membership.setStartDate(start);
        membership.setEndDate(end);
        membership.setStatus("active");
        membership.setAutoRenew(false);

        membershipRepository.save(membership);
        return toMembershipResponse(membership);
    }

    public MembershipResponse getMyActiveMembership() {
        User current = getCurrentUser();
        LocalDate today = LocalDate.now();

        Membership membership = membershipRepository
                .findFirstByUserIdAndStatusAndEndDateGreaterThanEqualOrderByEndDateDesc(
                        current.getId(), "active", today
                )
                .orElseThrow(() -> new IllegalArgumentException("No active membership"));

        return toMembershipResponse(membership);
    }

    public Page<MembershipResponse> listMembership(UUID userId, String status, Pageable pageable) {
        Page<Membership> page;

        if(userId != null && status != null && !status.isBlank()) {
            page = membershipRepository.findAllByUserIdAndStatus(userId, status, pageable);
        } else if(userId != null) {
            page = membershipRepository.findAllByUserId(userId, pageable);
        } else if(status != null && !status.isBlank()) {
            page = membershipRepository.findAllByStatus(status, pageable);
        } else {
            page = membershipRepository.findAll(pageable);
        }

        return page.map(this::toMembershipResponse);
    }

    public void updateStatus(UUID id, String newStatus) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found"));
        membership.setStatus(newStatus);
        membershipRepository.save(membership);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof SecurityUser principal)) {
            throw new IllegalStateException("No authenticated user");
        }
        return principal.getDomainUser();
    }

    private MembershipPlanResponse toPlanResponse(MembershipPlan p) {
        return new MembershipPlanResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getDurationDays(),
                p.getPrice(),
                p.getCurrency(),
                p.getBenefits(),
                p.getIsActive()
        );
    }

    private MembershipResponse toMembershipResponse(Membership m) {
        return new MembershipResponse(
                m.getId(),
                m.getUserId(),
                m.getPlanId(),
                m.getBranchId(),
                m.getStartDate(),
                m.getEndDate(),
                m.getStatus(),
                m.getAutoRenew()
        );
    }
}
