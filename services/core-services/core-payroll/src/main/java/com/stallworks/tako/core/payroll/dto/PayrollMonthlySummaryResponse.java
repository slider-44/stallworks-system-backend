package com.stallworks.tako.core.payroll.dto;

import java.math.BigDecimal;

public record PayrollMonthlySummaryResponse(
	    Long employeeId,
	    String employeeName,
	    Long branchId,
	    String branchName,
	    BigDecimal hourlyRate,
	    int daysWorked,
	    BigDecimal totalHours,
	    BigDecimal totalEarned
	) {}