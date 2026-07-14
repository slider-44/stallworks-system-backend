package com.stallworks.tako.core.sales.service;

import java.util.List;

import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;

public interface SalesReportService {
	
	SalesReportResponse create(SalesReportRequest salesReportRequest);
	
  List<SalesReportResponse> findAll();

}
