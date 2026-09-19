package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryAdvanceResponse(
        Long id,
        Long branchId,
        LocalDate date,
        Long employeeId,
        BigDecimal amount,
        String note,
        Long createdBy) {
}
