package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;

public record SaleLineResponse(
        String productId,
        String productName,      // "SOLO 320CC/450ML" — resolved server-side for display
        Integer pieces,          // 4, 8, 12... the fixed piece-count from the catalog
        Integer pcsSold,
        BigDecimal unitPrice,    // resolved (or the add-on price the client sent)
        BigDecimal lineTotal     // pcsSold * unitPrice, computed server-side
) {}