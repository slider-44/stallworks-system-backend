package com.stallworks.tako.core.inventory.dto;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.inventory.entity.PurchaseOrder;

@Component
public class PurchaseOrderMapper {
    
    public PurchaseOrder toEntity(PurchaseOrderRequest request) {
	
	 return PurchaseOrder.builder()
	            .branchId(request.branchId())
	            .date(request.date())
	            .totalCost(request.totalCost())
	            .paymentMethod(request.paymentMethod())
	            .notes(request.notes())
	            .createdBy(request.employeeId())
                .build();
    }
    
    
    public PurchaseOrderResponse toResponse(PurchaseOrder entity) {
        return new PurchaseOrderResponse(
                entity.getId(),
                entity.getBranchId(),
                entity.getDate(),
                entity.getTotalCost(),
                entity.getPaymentMethod(),
                entity.getCreatedBy());
    }

}
