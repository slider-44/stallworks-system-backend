package com.stallworks.tako.core.sales.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.sales.dto.BillCountLineResponse;
import com.stallworks.tako.core.sales.dto.CashSummaryResponse;
import com.stallworks.tako.core.sales.entity.CashSummary;

@Component
public class CashSummaryMapper {

    public CashSummaryResponse toResponse(
            CashSummary summary, BigDecimal actualCash, BigDecimal cashRemittance, BigDecimal totalRemittance) {

	
	 List<BillCountLineResponse> lines = summary.getBillCounts().stream()
		 .map(line -> new BillCountLineResponse(
			 line.getDenomination(),
			 line.getCount()
			 ))
		 .toList();
	 
	 
	  return new CashSummaryResponse( 
	                summary.getId(), 
	                summary.getDate(), 
	                summary.getBranchId(),
	                summary.getPettyCashYesterday(), 
	                summary.getGcash(), 
	                summary.getPettyCashNextday(),
	                lines, 
	                actualCash, 
	                cashRemittance, 
	                summary.getGcash(), 
	                totalRemittance,
	                summary.getClosed(),    
	                summary.getClosedAt(), 
	                summary.getClosedBy()
	        );
	
	

    }

}
