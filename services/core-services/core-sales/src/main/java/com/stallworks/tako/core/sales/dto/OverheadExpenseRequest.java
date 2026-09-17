package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record OverheadExpenseRequest(
	@NotNull Long branchId,
        @NotNull LocalDate date,
        @NotNull String description,
        @NotNull BigDecimal amount,
        @NotNull Long employeeId
	) {
    
    

}
