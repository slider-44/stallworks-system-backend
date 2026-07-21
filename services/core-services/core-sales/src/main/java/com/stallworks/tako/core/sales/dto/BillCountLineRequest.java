package com.stallworks.tako.core.sales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BillCountLineRequest(
	    @NotNull Integer denomination,
	    @NotNull @Min(0) Integer count
	) {}