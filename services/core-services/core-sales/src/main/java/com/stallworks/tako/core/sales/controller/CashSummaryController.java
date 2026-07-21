package com.stallworks.tako.core.sales.controller;



import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.sales.dto.CashSummaryRequest;
import com.stallworks.tako.core.sales.dto.CashSummaryResponse;
import com.stallworks.tako.core.sales.entity.CloseShiftRequest;
import com.stallworks.tako.core.sales.service.CashSummaryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cash-summaries")
@RequiredArgsConstructor
public class CashSummaryController {
    
    private final CashSummaryService  cashSummaryService;
    
    
    @PostMapping
    public ResponseEntity<CashSummaryResponse> create(@RequestBody CashSummaryRequest request) {
	
	CashSummaryResponse summary = cashSummaryService.save(request);
	
	return ResponseEntity.ok(summary);
	
    
    }
    
    @GetMapping
    public ResponseEntity<CashSummaryResponse> getByDateAndBranch(
            @RequestParam LocalDate date, @RequestParam Long branchId) {
	
	return cashSummaryService.findByDateAndBranch(date, branchId)
		.map(ResponseEntity:: ok)
		  .orElse(ResponseEntity.notFound().build());
    
    
    }
    
    @PostMapping("/close")
    public ResponseEntity<CashSummaryResponse> close(@RequestBody @Valid CloseShiftRequest request) {
        return ResponseEntity.ok(cashSummaryService.closeShift(request));
    }
    

}
