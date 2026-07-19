package com.stallworks.tako.core.sales.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record ExpenseBatchRequest( 
		@NotEmpty @Valid List<DailyExpenseRequest> expenses) {
}
