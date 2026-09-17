package com.stallworks.tako.core.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyBreakdownEntry(
        LocalDate date,
        BigDecimal sales,
        BigDecimal purchases,
        BigDecimal expenses,
        BigDecimal payroll,
        BigDecimal net) {
}
