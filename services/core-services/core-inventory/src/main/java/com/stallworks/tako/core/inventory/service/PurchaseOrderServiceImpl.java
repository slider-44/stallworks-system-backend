package com.stallworks.tako.core.inventory.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.inventory.dto.PurchaseOrderMapper;
import com.stallworks.tako.core.inventory.dto.PurchaseOrderRequest;
import com.stallworks.tako.core.inventory.dto.PurchaseOrderResponse;
import com.stallworks.tako.core.inventory.entity.PurchaseOrder;
import com.stallworks.tako.core.inventory.repository.PurchaseOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    
    private final PurchaseOrderRepository purchaseOrderRepository;
    
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {
	
	 PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrderMapper.toEntity(request));
	 return purchaseOrderMapper.toResponse(saved);
    }

    @Override
    public List<PurchaseOrderResponse> findByDateAndBranch(LocalDate date, Long branchId) {
	
	return purchaseOrderRepository.findByDateAndBranchId(date, branchId)
		.stream()
		.map(purchaseOrderMapper::toResponse)
		.toList();
	
    }

    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {
	
	PurchaseOrder existing  = purchaseOrderRepository.findById(id)
		 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
	                        "Purchase order not found: " + id));
	
	
	 existing.setBranchId(request.branchId());
	 existing.setDate(request.date());
	 existing.setPaymentMethod(request.paymentMethod());
	 existing.setNotes(request.notes());
	 existing.setTotalCost(request.totalCost());
	 
	 return purchaseOrderMapper.toResponse(purchaseOrderRepository.save(existing));
		
	 
    }

    @Override
    public void delete(Long id) {
	
	 if (!purchaseOrderRepository.existsById(id)) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase order not found: " + id);
	  }
	  purchaseOrderRepository.deleteById(id);
	
    }

    @Override
    public BigDecimal sumForMonth(YearMonth month, Long branchId) {
	  LocalDate from = month.atDay(1);
	  LocalDate to = month.atEndOfMonth();
	  
	   List<PurchaseOrder> records = branchId != null
	                ? purchaseOrderRepository.findByDateBetweenAndBranchId(from, to, branchId)
	                : purchaseOrderRepository.findByDateBetween(from, to);
	   
	   return records.stream()
		   .map(PurchaseOrder::getTotalCost)
		   .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public List<PurchaseOrderResponse> findForMonth(YearMonth month, Long branchId) {
	
	LocalDate from = month.atDay(1);
	LocalDate to = month.atEndOfMonth();
	
	List<PurchaseOrder> records  = branchId != null
		   ? purchaseOrderRepository.findByDateBetweenAndBranchId(from, to, branchId)
	           : purchaseOrderRepository.findByDateBetween(from, to);
	
	return records.stream()
		  .map(purchaseOrderMapper::toResponse)
	          .toList();
	
    }

}
