package com.stallworks.tako.core.payroll.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.payroll.dto.PayrollDailyResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlyDetailResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlySummaryResponse;
import com.stallworks.tako.core.payroll.service.PayrollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping("/daily")
    public ResponseEntity<PayrollDailyResponse> daily(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(payrollService.calculateDaily(employeeId, date));
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<List<PayrollMonthlySummaryResponse>> monthly(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
            @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(payrollService.calculateMonthly(month, branchId));
    }

    @GetMapping("/monthly/{employeeId}")
    public ResponseEntity<PayrollMonthlyDetailResponse> monthlyDetail(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity.ok(payrollService.monthlyDetail(employeeId, month));
    }
}
