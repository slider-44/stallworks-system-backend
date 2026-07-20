package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DailyExpenseRequest(  
	    @NotNull Long branchId,
	    @NotNull LocalDate date,
	    @NotNull String description,
	    BigDecimal amount) {

}
