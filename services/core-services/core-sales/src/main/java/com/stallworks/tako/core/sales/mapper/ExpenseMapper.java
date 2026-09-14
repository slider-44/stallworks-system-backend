package com.stallworks.tako.core.sales.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.sales.dto.DailyExpenseRequest;
import com.stallworks.tako.core.sales.dto.DailyExpenseResponse;
import com.stallworks.tako.core.sales.entity.DailyExpense;

@Component
public class ExpenseMapper {

	public DailyExpense toEntity(DailyExpenseRequest expense) {
		
		return DailyExpense.builder()
                    .date(expense.date())
                    .branchId(expense.branchId())
                    .description(expense.description())
                    .amount(expense.amount())
                    .category(expense.category())
                    .build();
				
		
	}
	
	 public DailyExpenseResponse toResponse(DailyExpense entity) {
		 return new DailyExpenseResponse(
	                entity.getBranchId(),
	                entity.getDate(),
	                entity.getDescription(),
	                entity.getAmount(),
	                entity.getCategory()
	        );
	    }
	 
	// helper for batch
	    public List<DailyExpense> toEntityList(List<DailyExpenseRequest> requests) {
	        return requests.stream()
	                .map(this::toEntity)
	                .toList();
	    }
}
