package com.stallworks.tako.core.inventory.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record UpdateContainerPriceRequest(
        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)   // must be > 0
        BigDecimal price
) {}