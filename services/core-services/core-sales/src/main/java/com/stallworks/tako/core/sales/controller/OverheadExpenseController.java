package com.stallworks.tako.core.sales.controller;

import java.time.YearMonth;
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

import com.stallworks.tako.core.sales.dto.OverheadExpenseRequest;
import com.stallworks.tako.core.sales.dto.OverheadExpenseResponse;
import com.stallworks.tako.core.sales.service.OverheadExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/overhead-expenses")
@RequiredArgsConstructor
public class OverheadExpenseController {
    
    private final OverheadExpenseService overheadExpenseService;
    
    @PostMapping
    public ResponseEntity<OverheadExpenseResponse> create(@RequestBody @Valid OverheadExpenseRequest request) {
	
	return ResponseEntity.ok(overheadExpenseService.create(request));
	
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<List<OverheadExpenseResponse>> listForMonth(
	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
	    @RequestParam(required = false) Long branchId
	    ) {
	
	  return ResponseEntity.ok(overheadExpenseService.findForMonth(month, branchId));
	
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<OverheadExpenseResponse> update(
	    @PathVariable Long id,
	     @RequestBody @Valid OverheadExpenseRequest request ) {
	
	return ResponseEntity.ok(overheadExpenseService.update(id, request));
	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
	
	 overheadExpenseService.delete(id);
	 return ResponseEntity.noContent().build();
	
    }
    
    

}
