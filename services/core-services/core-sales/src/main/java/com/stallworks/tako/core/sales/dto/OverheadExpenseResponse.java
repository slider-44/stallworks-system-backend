package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OverheadExpenseResponse(
        Long id,
        Long branchId,
        LocalDate date,
        String description,
        BigDecimal amount,
        Long createdBy) {
}
