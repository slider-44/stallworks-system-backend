package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import com.stallworks.tako.core.sales.dto.OverheadExpenseRequest;
import com.stallworks.tako.core.sales.dto.OverheadExpenseResponse;

public interface OverheadExpenseService {
    
    OverheadExpenseResponse create(OverheadExpenseRequest request);
    
    List<OverheadExpenseResponse> findForMonth(YearMonth month, Long branchId);
    
    OverheadExpenseResponse update(Long id, OverheadExpenseRequest request);

    void delete(Long id);

    BigDecimal sumForMonth(YearMonth month, Long branchId);
    
    

}
