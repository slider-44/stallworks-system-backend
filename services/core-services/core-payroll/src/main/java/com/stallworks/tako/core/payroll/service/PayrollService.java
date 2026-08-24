package com.stallworks.tako.core.payroll.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.stallworks.tako.core.payroll.dto.PayrollDailyResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlyDetailResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlySummaryResponse;

public interface PayrollService {
    PayrollDailyResponse calculateDaily(Long employeeId, LocalDate date);
    List<PayrollMonthlySummaryResponse> calculateMonthly(YearMonth month, Long branchId);
    PayrollMonthlyDetailResponse monthlyDetail(Long employeeId, YearMonth month);

}
