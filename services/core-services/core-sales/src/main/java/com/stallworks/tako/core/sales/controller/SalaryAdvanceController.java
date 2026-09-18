package com.stallworks.tako.core.sales.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.stallworks.tako.core.sales.dto.SalaryAdvanceRequest;
import com.stallworks.tako.core.sales.dto.SalaryAdvanceResponse;
import com.stallworks.tako.core.sales.service.SalaryAdvanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/salary-advances")
@RequiredArgsConstructor
public class SalaryAdvanceController {
    
    private final SalaryAdvanceService salaryAdvanceService;
    
    @PostMapping
    public ResponseEntity<SalaryAdvanceResponse> create(@RequestBody @Valid SalaryAdvanceRequest request) {
        return ResponseEntity.ok(salaryAdvanceService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SalaryAdvanceResponse>> list(
            @RequestParam LocalDate date, @RequestParam Long branchId) {
        return ResponseEntity.ok(salaryAdvanceService.findByDateAndBranch(date, branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryAdvanceResponse> update(
            @PathVariable Long id, @RequestBody @Valid SalaryAdvanceRequest request) {
        return ResponseEntity.ok(salaryAdvanceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaryAdvanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
