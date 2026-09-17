package com.stallworks.tako.core.sales.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import com.stallworks.tako.core.sales.dto.CashSummaryRequest;
import com.stallworks.tako.core.sales.dto.CashSummaryResponse;
import com.stallworks.tako.core.sales.dto.RemittanceMonthlyTotals;
import com.stallworks.tako.core.sales.entity.CloseShiftRequest;

public interface CashSummaryService {
    
    CashSummaryResponse save(CashSummaryRequest request);
    
    Optional<CashSummaryResponse> findByDateAndBranch(LocalDate date, Long branchId);
    
    CashSummaryResponse closeShift(CloseShiftRequest request);
    
    Optional<CashSummaryResponse> findPreviousForBranch(LocalDate date, Long branchId);
    
    RemittanceMonthlyTotals sumRemittanceForMonth(YearMonth month, Long branchId);


}
