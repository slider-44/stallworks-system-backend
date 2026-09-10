package com.stallworks.tako.core.reports.service;

import java.time.YearMonth;
import java.util.List;

import com.stallworks.tako.core.reports.dto.DailyBreakdownEntry;
import com.stallworks.tako.core.reports.dto.MonthlySummaryResponse;

public interface MonthlyReportService {
    
    MonthlySummaryResponse monthlySummary(YearMonth month, Long branchId);
    
    List<DailyBreakdownEntry> dailyBreakdown(YearMonth month, Long branchId);

}
