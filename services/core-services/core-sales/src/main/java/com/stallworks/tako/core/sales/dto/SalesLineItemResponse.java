package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;

import com.stallworks.tako.core.sales.enums.ContainerSize;

public record SalesLineItemResponse(
	    ContainerSize containerSize, 
	    BigDecimal unitPrice, 
	    Integer quantitySold, 
	    BigDecimal lineTotal
	) {}
