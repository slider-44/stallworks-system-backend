package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record SaleResponse(
        String id,
        String branchId,
        String crewId,
        LocalDate saleDate,
        LocalTime timeIn,
        LocalTime timeOut,
        List<SaleLineResponse> lineItems,
        BigDecimal grandTotal,
        Instant recordedAt
) {}
