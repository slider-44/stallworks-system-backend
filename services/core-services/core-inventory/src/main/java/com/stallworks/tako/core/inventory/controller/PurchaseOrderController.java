package com.stallworks.tako.core.inventory.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stallworks.tako.core.inventory.dto.PurchaseOrderRequest;
import com.stallworks.tako.core.inventory.dto.PurchaseOrderResponse;
import com.stallworks.tako.core.inventory.service.PurchaseOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/purchase-orders")
@RestController
@RequiredArgsConstructor
public class PurchaseOrderController {
    
    
    private final PurchaseOrderService purchaseOrderService;
    
    
    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(
	    @RequestBody @Valid PurchaseOrderRequest  orderRequest ) {
	
	  return ResponseEntity.ok(purchaseOrderService.create(orderRequest));
    }
    
    
    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> list(
	    @RequestParam LocalDate date,
	    @RequestParam Long branchId) {
	
	
	return ResponseEntity.ok(purchaseOrderService.findByDateAndBranch(date, branchId));
	
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> update(
	    @PathVariable Long id,
	    @RequestBody @Valid PurchaseOrderRequest request) {
	
	
	return ResponseEntity.ok(purchaseOrderService.update(id, request));
	
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
	    @PathVariable Long id) {
	
	 purchaseOrderService.delete(id);
	 return ResponseEntity.noContent().build();
	
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<List<PurchaseOrderResponse>> listForMonth(
	    @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
	    @RequestParam(required= false) Long branchId
	    ) {
	
	return ResponseEntity.ok(purchaseOrderService.findForMonth(month, branchId));
	
    }
    
    
    

}
