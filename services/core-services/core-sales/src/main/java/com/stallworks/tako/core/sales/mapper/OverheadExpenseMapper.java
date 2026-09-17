package com.stallworks.tako.core.sales.mapper;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.sales.dto.OverheadExpenseRequest;
import com.stallworks.tako.core.sales.dto.OverheadExpenseResponse;
import com.stallworks.tako.core.sales.entity.OverheadExpense;

@Component
public class OverheadExpenseMapper {

    public OverheadExpense  toEntity(OverheadExpenseRequest request) {
	 return OverheadExpense.builder()
	                .branchId(request.branchId())
	                .date(request.date())
	                .description(request.description())
	                .amount(request.amount())
	                .createdBy(request.employeeId())
	                .build();
	
    }
    
    public OverheadExpenseResponse toResponse(OverheadExpense entity) {
	
	return new OverheadExpenseResponse(
                entity.getId(),
                entity.getBranchId(),
                entity.getDate(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCreatedBy());
	
    }
    
}
