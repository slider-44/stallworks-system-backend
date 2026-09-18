package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.stallworks.tako.core.sales.dto.SalaryAdvanceRequest;
import com.stallworks.tako.core.sales.dto.SalaryAdvanceResponse;

public interface SalaryAdvanceService {

    SalaryAdvanceResponse create(SalaryAdvanceRequest request);

    List<SalaryAdvanceResponse> findByDateAndBranch(LocalDate date, Long branchId);

    SalaryAdvanceResponse update(Long id, SalaryAdvanceRequest request);

    void delete(Long id);

    BigDecimal sumForDateAndBranch(LocalDate date, Long branchId);
}
