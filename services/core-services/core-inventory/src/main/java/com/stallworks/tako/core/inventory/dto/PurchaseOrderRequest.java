package com.stallworks.tako.core.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record PurchaseOrderRequest(
	@NotNull Long branchId, 
	@NotNull LocalDate date,
	@NotNull BigDecimal totalCost,
	@NotNull PaymentMethod paymentMethod,
	String notes,
	@NotNull Long employeeId) {
    
    

}
