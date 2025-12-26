package com.codemystack.apps.supagym.api.attendance;

import com.codemystack.apps.supagym.api.attendance.dto.AttendanceResponse;
import com.codemystack.apps.supagym.api.attendance.dto.CheckInRequest;
import com.codemystack.apps.supagym.api.attendance.dto.CheckOutRequest;
import com.codemystack.apps.supagym.api.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@RequestBody CheckInRequest request) {
        System.out.println(">>> /api/attendance/check-in handler");
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(@RequestBody CheckOutRequest request) {
        return ResponseEntity.ok(attendanceService.checkOut(request));
    }
}
