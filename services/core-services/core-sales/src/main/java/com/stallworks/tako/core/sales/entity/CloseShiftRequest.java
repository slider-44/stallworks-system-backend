package com.stallworks.tako.core.sales.entity;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CloseShiftRequest(
	    @NotNull LocalDate date,
	    @NotNull Long branchId,
	    @NotNull Long employeeId
	) {}
