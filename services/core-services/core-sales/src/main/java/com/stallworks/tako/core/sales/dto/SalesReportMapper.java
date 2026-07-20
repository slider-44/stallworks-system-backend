package com.stallworks.tako.core.sales.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.sales.entity.SalesReport;

@Component
public class SalesReportMapper {
	
	public SalesReportResponse toResponse(SalesReport report) {
	    List<SalesLineItemResponse> items = report.getLineItems().stream()
                .map(li -> new SalesLineItemResponse(
                        li.getContainerSize(),
                        li.getUnitPrice(),
                        li.getQuantitySold(),
                        li.getLineTotal()))
                .toList();

	    return new SalesReportResponse(
	                report.getId(),
	                report.getBranchId(),
	                report.getEmployeeId(),
	                report.getDate(),
	                report.getTimeIn(),
	                report.getTimeOut(),
	                items,
	                report.getTotalSales(),
	                report.getCreatedAt(),
	                report.getCreatedBy(),
	                report.getUpdatedBy()
	               
	        );
	}

}
