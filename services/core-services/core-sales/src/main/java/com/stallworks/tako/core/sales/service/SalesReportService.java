package com.stallworks.tako.core.sales.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.stallworks.tako.core.sales.dto.SalesReportRequest;
import com.stallworks.tako.core.sales.dto.SalesReportResponse;

public interface SalesReportService {
	
  SalesReportResponse create(SalesReportRequest salesReportRequest);
	
  List<SalesReportResponse> findAll();
  
  Optional<SalesReportResponse> findByDateAndBranch(LocalDate date, Long branchId) ;
  
  

}
