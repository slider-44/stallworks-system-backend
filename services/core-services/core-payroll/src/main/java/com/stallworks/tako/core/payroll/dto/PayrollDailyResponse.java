package com.stallworks.tako.core.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record PayrollDailyResponse(
	    Long employeeId,
	    LocalDate date,
	    LocalTime timeIn,
	    LocalTime timeOut,
	    boolean shiftOpen,
	    BigDecimal hoursWorked,
	    BigDecimal hourlyRate,
	    BigDecimal dailySalary
	) {}