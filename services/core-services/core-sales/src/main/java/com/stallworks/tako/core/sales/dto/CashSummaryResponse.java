package com.stallworks.tako.core.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CashSummaryResponse( 
	    Long id,
	    LocalDate date,
	    Long branchId,
	    BigDecimal pettyCashYesterday,
	    BigDecimal gcash,
	    BigDecimal pettyCashNextday,
	    List<BillCountLineResponse> billCounts,
	    
	
	    // Server-computed — the frontend currently recalculates these itself
	    // from billCounts, but returning them here means the backend stays
	    // the single source of truth for money math, not just persistence.
	    BigDecimal actualCash,       // sum(denomination × count)
	    BigDecimal cashRemittance,   // actualCash − pettyCashNextday
	    BigDecimal gcashRemittance,  // same as gcash, kept for symmetry
	    BigDecimal totalRemittance,  // cashRemittance + gcashRemittance
	
	    Boolean closed, 
	    LocalDateTime closedAt, 
	    Long closedBy
	    
	) {

}
