package com.stallworks.tako.core.reports.dto;

import java.math.BigDecimal;

public record MonthlySummaryResponse(
        String month,
        Long branchId,
        String branchName,

        BigDecimal totalSales,          // Revenue
        BigDecimal totalPurchaseOrders, // Cost of Goods — inventory purchases only
        BigDecimal cogsExpenses,        // Cost of Goods — expense entries tagged COGS (oil, packaging, etc.)
        BigDecimal costOfGoods,         // totalPurchaseOrders + cogsExpenses — what the "Cost of Goods" card shows
        BigDecimal overheadExpenses,    // Expense entries tagged OVERHEAD (rent, electricity, etc.)
        BigDecimal totalPayroll,        // Salary
        BigDecimal netProfit,

        // Informational only — not part of netProfit above.
        BigDecimal cashRemitted,
        BigDecimal gcashRemitted,
        BigDecimal totalRemitted) {
}
