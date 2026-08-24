package com.stallworks.tako.core.sales.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CloseShiftRequest(
	    @NotNull LocalDate date,
	    @NotNull Long branchId,
	    @NotNull Long employeeId,
	    @NotNull ClosingStatus status,
	    @NotNull BigDecimal difference,
	    // Required only when status isn't BALANCED — enforced in
	    // CashSummaryServiceImpl, not here, since that's a conditional rule
	    // bean validation annotations can't express cleanly on their own.
	    String note
	) {}
