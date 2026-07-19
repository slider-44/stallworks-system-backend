package com.stallworks.tako.core.sales.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.stallworks.tako.core.sales.dto.DailyExpenseRequest;
import com.stallworks.tako.core.sales.dto.DailyExpenseResponse;
import com.stallworks.tako.core.sales.dto.ExpenseBatchRequest;

public interface DailyExpenseService {
	
    List<DailyExpenseResponse> createAll(ExpenseBatchRequest requests);
    
    List<DailyExpenseResponse> findByDateAndBranch(LocalDate date, Long branchId);
	

    List<DailyExpenseResponse> findAll();
}
