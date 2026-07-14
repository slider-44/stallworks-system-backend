package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;

import com.stallworks.tako.core.sales.enums.ContainerSize;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SalesLineItemRequest(
	    @NotNull ContainerSize containerSize,
	    @NotNull @Min(0) Integer quantitySold,
	    BigDecimal manualUnitPrice // only used/required when containerSize == ADD_ONS
	) {}
