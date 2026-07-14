package com.stallworks.tako.core.sales.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SalesReportRequest(
	    @NotNull Long employeeId,
	    @NotNull Long branchId,
	    @NotNull LocalDate date,
	    @NotNull LocalTime timeIn,
	    @NotNull LocalTime timeOut,
	    @NotEmpty @Valid List<SalesLineItemRequest> lineItems
	) {}
