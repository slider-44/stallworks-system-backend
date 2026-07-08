package com.stallworks.tako.core.inventory.dto;

import java.math.BigDecimal;

public record ContainerResponse(
        Long id,
        String containerSize,   // "SOLO"
        String description,     // "320CC/450ML"
        int pieces,             // 4
        String piecesLabel,     // "4 PCS" — derived: pieces + " PCS"
        BigDecimal price,
        boolean active
) {}