package com.stallworks.tako.core.reports.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.reports.dto.DailyBreakdownEntry;
import com.stallworks.tako.core.reports.dto.MonthlySummaryResponse;
import com.stallworks.tako.core.reports.service.MonthlyReportService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/reports")
@RestController
@RequiredArgsConstructor
public class MonthlyReportController {
    
    private final MonthlyReportService monthlyReportService;
    
    @GetMapping("/monthly-summary")
    public ResponseEntity<MonthlySummaryResponse> monthlySummary(
	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
	    @RequestParam(required = false) Long branchId
	    ) {
	
	
	return ResponseEntity.ok(monthlyReportService.monthlySummary(month, branchId));
	
    }
    
    @GetMapping("/monthly-summary/daily")
    public ResponseEntity<List<DailyBreakdownEntry>> dailyBreakdown(
	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
	    @RequestParam(required=false) Long branchId
	    ) {
	
	
	return ResponseEntity.ok(monthlyReportService.dailyBreakdown(month, branchId));
	
    }
    
    

}
