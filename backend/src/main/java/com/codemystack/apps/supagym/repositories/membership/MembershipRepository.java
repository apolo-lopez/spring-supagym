package com.codemystack.apps.supagym.repositories.membership;

import com.codemystack.apps.supagym.models.membership.Membership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByUserIdAndStatus(UUID memberId, String status);
    List<Membership> findByEndDateAndStatus(LocalDate endDate, String status);
    Page<Membership> findAllByUserId(UUID userId, Pageable pageable);
    Page<Membership> findAllByStatus(String status, Pageable pageable);
    Page<Membership> findAllByUserIdAndStatus(UUID userId, String status, Pageable pageable);
    Optional<Membership> findFirstByUserIdAndStatusAndEndDateGreaterThanEqualOrderByEndDateDesc(
            UUID memberId,
            String status,
            LocalDate endDate
    );
}
