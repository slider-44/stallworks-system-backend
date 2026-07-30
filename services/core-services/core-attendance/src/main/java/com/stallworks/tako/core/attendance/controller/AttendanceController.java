package com.stallworks.tako.core.attendance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.dto.ClockInRequest;
import com.stallworks.tako.core.attendance.dto.ClockOutRequest;
import com.stallworks.tako.core.attendance.service.AttendanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    
    private final AttendanceService service;
    
    @GetMapping("/today")
    public ResponseEntity<AttendanceResponse> today(
	    @RequestParam Long employeeId) {
	
        return service.findToday(employeeId)
        		.map(ResponseEntity::ok)
        		.orElse(ResponseEntity.notFound()
        	.build());
    }
    

    @PostMapping("/clock-in")
    public ResponseEntity<AttendanceResponse> clockIn(
	    @RequestBody @Valid ClockInRequest request) {
	
        return ResponseEntity.ok(service.clockIn(request.employeeId(), request.branchId()));
    }
    
    @PostMapping("/clock-out")
    public ResponseEntity<AttendanceResponse> clockOut(
	    @RequestBody @Valid ClockOutRequest request) {

        return ResponseEntity.ok(service.clockOut(request.employeeId()));
    }

    @GetMapping("/open")
    public ResponseEntity<List<AttendanceResponse>> openToday() {
	
	return ResponseEntity.ok(service.findOpenToday());
    
    
    }
    

}
