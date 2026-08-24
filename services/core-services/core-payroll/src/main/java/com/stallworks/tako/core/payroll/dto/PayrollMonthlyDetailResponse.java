package com.stallworks.tako.core.payroll.dto;

import java.math.BigDecimal;
import java.util.List;

public record PayrollMonthlyDetailResponse(
	    Long employeeId,
	    String employeeName,
	    Long branchId,
	    String branchName,
	    BigDecimal hourlyRate,
	    List<PayrollDailyEntryResponse> days,
	    BigDecimal totalEarned
	) {}
