package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CashSummaryRequest(
	    @NotNull LocalDate date,
	    @NotNull Long branchId,
	    @NotNull Long actorEmployeeId, 
	    BigDecimal pettyCashYesterday,
	    BigDecimal gcash,
	    BigDecimal pettyCashNextday,
	    @Valid List<BillCountLineRequest> billCounts
	    ) {

}
