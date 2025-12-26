package com.codemystack.apps.supagym.api.membership;

import com.codemystack.apps.supagym.api.membership.dto.*;
import com.codemystack.apps.supagym.api.membership.service.MembershipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/plans")
    public ResponseEntity<List<MembershipPlanResponse>> listPlans() {
        return ResponseEntity.ok(membershipService.listActivePlans());
    }

    @PostMapping("/plans")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<MembershipPlanResponse> createPlan(
            @RequestBody MembershipPlanCreateRequest request
            ) {
        return ResponseEntity.status(201).body(
                membershipService.createPlan(request)
        );
    }

    // Memberships
    @GetMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public Page<MembershipResponse> listMembership(
            @RequestParam(required = false)UUID userId,
            @RequestParam(required = false) String status,
            Pageable pageable
            ) {
        return membershipService.listMembership(userId, status, pageable);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public void updateMembershipStatus(
            @PathVariable UUID id,
            @RequestBody UpdateMembershipStatusRequest request) {
        membershipService.updateStatus(id, request.status());
    }


    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<MembershipResponse> create(
            @RequestBody MembershipCreateRequest request) {
        return ResponseEntity.status(201).body(membershipService.createMembership(request));
    }

    @GetMapping("/me/active")
    public ResponseEntity<MembershipResponse> myActive() {
        return ResponseEntity.ok(membershipService.getMyActiveMembership());
    }
}
