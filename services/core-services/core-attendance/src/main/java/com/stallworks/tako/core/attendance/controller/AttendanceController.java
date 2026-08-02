package com.stallworks.tako.core.attendance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.attendance.dto.AttendanceResponse;
import com.stallworks.tako.core.attendance.dto.AttendanceUpdateRequest;
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

    // Backs the "This Week" table on the Time Clock page — a rolling
    // window the frontend computes (e.g. last 7 days), not a fixed
    // Mon–Sun calendar week, so it stays simple on both ends.
    @GetMapping("/history")
    public ResponseEntity<List<AttendanceResponse>> history(
	    @RequestParam Long employeeId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

	return ResponseEntity.ok(service.findHistory(employeeId, from, to));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> update(@PathVariable Long id,
	    @RequestBody @Valid AttendanceUpdateRequest request) {
	
	return ResponseEntity.ok(service.update(id, request, request.updatedBy()));
	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
	service.delete(id);
	return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> list(
    	@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    	@RequestParam(required = false) Long branchId,
    	@RequestParam(required = false) Long employeeId) {

        return ResponseEntity.ok(service.findByFilters(date, branchId, employeeId));
    }

}
