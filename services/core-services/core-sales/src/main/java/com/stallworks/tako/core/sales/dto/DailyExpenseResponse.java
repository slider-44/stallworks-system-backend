package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyExpenseResponse(	
		Long branchId,
	    LocalDate date,
	    String description,
	    BigDecimal amount) {

}


