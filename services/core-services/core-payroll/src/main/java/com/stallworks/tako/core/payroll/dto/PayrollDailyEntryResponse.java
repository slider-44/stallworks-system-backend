package com.stallworks.tako.core.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record PayrollDailyEntryResponse(
	    LocalDate date,
	    LocalTime timeIn,
	    LocalTime timeOut,
	    boolean shiftOpen,
	    BigDecimal hours,
	    BigDecimal earned
	) {}
