package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record SalesReportResponse (
        Long id,
        Long branchId,
        Long employeeId,
        LocalDate date,
        LocalTime timeIn,
        LocalTime timeOut,
        List<SalesLineItemResponse> lineItems,
        BigDecimal totalSales,
        LocalDateTime createdAt,
        Long createdBy, 
        Long updatedBy
) {}
