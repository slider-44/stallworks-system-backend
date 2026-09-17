package com.stallworks.tako.core.inventory.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.stallworks.tako.core.inventory.dto.PurchaseOrderRequest;
import com.stallworks.tako.core.inventory.dto.PurchaseOrderResponse;

public interface PurchaseOrderService {
    
    PurchaseOrderResponse create(PurchaseOrderRequest request);
    
    List<PurchaseOrderResponse> findByDateAndBranch(LocalDate date, Long branchId);
    
    PurchaseOrderResponse update(Long id, PurchaseOrderRequest request);

    void delete(Long id);

    BigDecimal sumForMonth(YearMonth month, Long branchId);
    
    List<PurchaseOrderResponse> findForMonth(YearMonth month, Long branchId);

}
