package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaleLineRequest(
        @NotBlank String productId,        // which container size (or the add-on product)
        @NotNull @Min(0) Integer pcsSold,  // what the crew typed
        BigDecimal unitPrice               // null for fixed-price rows; set ONLY for open-priced add-ons
) {}