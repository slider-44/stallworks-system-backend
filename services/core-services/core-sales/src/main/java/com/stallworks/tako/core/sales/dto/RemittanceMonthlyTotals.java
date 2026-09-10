package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;

public record RemittanceMonthlyTotals(
        BigDecimal cashRemitted,
        BigDecimal gcashRemitted,
        BigDecimal totalRemitted) {
}