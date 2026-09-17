package com.stallworks.tako.core.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseOrderResponse(
        Long id,
        Long branchId,
        LocalDate date,
        BigDecimal totalCost,
        PaymentMethod paymentMethod,
        Long createdBy) {
}
