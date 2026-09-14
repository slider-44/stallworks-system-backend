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

import com.stallworks.tako.core.sales.dto.DailyExpenseRequest;
import com.stallworks.tako.core.sales.dto.DailyExpenseResponse;
import com.stallworks.tako.core.sales.dto.ExpenseBatchRequest;
import com.stallworks.tako.core.sales.service.DailyExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    
    private final DailyExpenseService expenseService;

    @PostMapping
    public ResponseEntity<List<DailyExpenseResponse>> create(
	    @RequestBody @Valid ExpenseBatchRequest request) {
       
	 List<DailyExpenseResponse> response = expenseService.createAll(request);
	 
	 return ResponseEntity.ok(response);
	
	
    }

    @GetMapping
    public ResponseEntity<List<DailyExpenseResponse>> getExpenses(
	    @RequestParam(required =false ) LocalDate date,
	    @RequestParam(required =false ) Long branchId) {
      
	if (date != null && branchId != null) {
	        return ResponseEntity.ok(expenseService.findByDateAndBranch(date, branchId));
	    }
	
	 return ResponseEntity.ok(expenseService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DailyExpenseResponse> update(
	    @PathVariable Long id, @RequestBody @Valid DailyExpenseRequest request) {
	return ResponseEntity.ok(expenseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
	expenseService.delete(id);
	return ResponseEntity.noContent().build();
    }

}
