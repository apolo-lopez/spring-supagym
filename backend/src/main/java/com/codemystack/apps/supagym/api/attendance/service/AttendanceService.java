package com.codemystack.apps.supagym.api.attendance.service;

import com.codemystack.apps.supagym.api.attendance.dto.AttendanceResponse;
import com.codemystack.apps.supagym.api.attendance.dto.CheckInRequest;
import com.codemystack.apps.supagym.api.attendance.dto.CheckOutRequest;
import com.codemystack.apps.supagym.models.attendance.Attendance;
import com.codemystack.apps.supagym.models.membership.Membership;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.repositories.attendance.AttendanceRepository;
import com.codemystack.apps.supagym.repositories.membership.MembershipRepository;
import com.codemystack.apps.supagym.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MembershipRepository membershipRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository, MembershipRepository membershipRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.membershipRepository = membershipRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        System.out.println("AUTH = " + authentication);
        if (authentication == null ||
                !(authentication.getPrincipal()
                        instanceof
                        SecurityUser principal)) {
            throw new IllegalArgumentException("No authenticated user");
        }

        System.out.println("USER PRINCIPAL = " + ((SecurityUser) authentication.getPrincipal()).getUsername());

        return principal.getDomainUser();
    }

    public AttendanceResponse checkIn(CheckInRequest request) {
        User user = getCurrentUser();

        LocalDate today = LocalDate.now();
        Membership activeMembership = membershipRepository
                .findFirstByUserIdAndStatusAndEndDateGreaterThanEqualOrderByEndDateDesc(
                        user.getId(),
                        "active",
                        today
                ).orElseThrow(()
                        -> new IllegalArgumentException("Membership not found"));

        LocalDateTime startOfDay = today.atStartOfDay();
        attendanceRepository.findFirstByUserIdAndCheckInAfterAndCheckOutIsNull(
                user.getId(),
                startOfDay
        ).ifPresent(a -> {
            throw new IllegalArgumentException("Already checked in. Please check out first");
        });

        Attendance attendance = new Attendance();
        attendance.setUserId(user.getId());
        attendance.setBranchId(user.getBranchId());
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setCheckInMethod(request.method());
        attendance.setNotes(request.notes());

        attendanceRepository.save(attendance);
        return toResponse(attendance);
    }

    public AttendanceResponse checkOut(CheckOutRequest request) {
        User user = getCurrentUser();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();

        Attendance attendance = attendanceRepository
                .findFirstByUserIdAndCheckInAfterAndCheckOutIsNull(
                        user.getId(), startOfDay
                )
                .orElseThrow(() ->
                        new IllegalStateException("No open check-in found"));

        attendance.setCheckOut(LocalDateTime.now());
        attendance.setCheckOutMethod(request.method());
        if (request.notes() != null && !request.notes().isBlank()) {
            attendance.setNotes(request.notes());
        }

        attendanceRepository.save(attendance);

        return toResponse(attendance);
    }

    private AttendanceResponse toResponse(Attendance a) {
        return new AttendanceResponse(
                a.getId(),
                a.getUserId(),
                a.getBranchId(),
                a.getCheckIn(),
                a.getCheckOut(),
                a.getCheckInMethod(),
                a.getCheckOutMethod(),
                a.getNotes()
        );
    }
}
