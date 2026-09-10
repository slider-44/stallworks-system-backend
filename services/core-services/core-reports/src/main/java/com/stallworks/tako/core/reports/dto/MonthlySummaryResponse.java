package com.stallworks.tako.core.reports.dto;

import java.math.BigDecimal;

public record MonthlySummaryResponse(
        String month,
        Long branchId,
        String branchName,

        BigDecimal totalSales,          // Revenue
        BigDecimal totalPurchaseOrders, // Cost of Goods
        BigDecimal totalExpenses,
        BigDecimal totalPayroll,        // Salary
        BigDecimal netProfit,

        // Informational only — not part of netProfit above.
        BigDecimal cashRemitted,
        BigDecimal gcashRemitted,
        BigDecimal totalRemitted) {
}
