package com.stallworks.tako.core.reports.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.attendance.entity.Attendance;
import com.stallworks.tako.core.attendance.repository.AttendanceRepository;
import com.stallworks.tako.core.common.entity.Branch;
import com.stallworks.tako.core.common.entity.Employee;
import com.stallworks.tako.core.common.repository.BranchRepository;
import com.stallworks.tako.core.common.repository.EmployeeRepository;
import com.stallworks.tako.core.inventory.dto.PurchaseOrderResponse;
import com.stallworks.tako.core.inventory.entity.PurchaseOrder;
import com.stallworks.tako.core.inventory.service.PurchaseOrderService;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlySummaryResponse;
import com.stallworks.tako.core.payroll.service.PayrollService;
import com.stallworks.tako.core.reports.dto.DailyBreakdownEntry;
import com.stallworks.tako.core.reports.dto.MonthlySummaryResponse;
import com.stallworks.tako.core.sales.dto.RemittanceMonthlyTotals;
import com.stallworks.tako.core.sales.entity.DailyExpense;
import com.stallworks.tako.core.sales.entity.SalesReport;
import com.stallworks.tako.core.sales.repository.DailyExpenseRepository;
import com.stallworks.tako.core.sales.repository.SalesReportRepository;
import com.stallworks.tako.core.sales.service.CashSummaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyReportServiceImpl implements MonthlyReportService {
    
    private final SalesReportRepository salesReportRepository;

    private final DailyExpenseRepository  dailyExpenseRepository;
    
    private final PurchaseOrderService purchaseOrderService;
    
    private final PayrollService payrollService;
    
    private final CashSummaryService cashSummaryService;
    
    private final BranchRepository branchRepository;
    
    private final AttendanceRepository attendanceRepository;
    
    private final EmployeeRepository employeeRepository;

    @Override
    public MonthlySummaryResponse monthlySummary(YearMonth month, Long branchId) {
	
	LocalDate from = month.atDay(1);
	LocalDate to = month.atEndOfMonth();
	
	List<SalesReport> sales = branchId != null
	                ? salesReportRepository.findByDateBetweenAndBranchId(from, to, branchId)
	                : salesReportRepository.findByDateBetween(from, to);
	
	BigDecimal totalSales  = sales.stream()
		.map(SalesReport:: getTotalSales)
		.reduce(BigDecimal.ZERO, BigDecimal::add);
	
	
	List<DailyExpense> expenses = branchId != null
		 ? dailyExpenseRepository.findByDateBetweenAndBranchId(from, to, branchId)
		 : dailyExpenseRepository.findByDateBetween(from, to);
	
	
	BigDecimal totalExpenses = expenses.stream()
		.map(DailyExpense::getAmount)
		.reduce(BigDecimal.ZERO, BigDecimal::add);
	
	
	BigDecimal totalPurchaseOrders = purchaseOrderService.sumForMonth(month, branchId);
	
	BigDecimal totalPayroll = payrollService.calculateMonthly(month, branchId).stream()
		.map(PayrollMonthlySummaryResponse:: totalEarned)
		.reduce(BigDecimal.ZERO, BigDecimal::add);
	
	RemittanceMonthlyTotals remittance = cashSummaryService.sumRemittanceForMonth(month, branchId);

	BigDecimal netProfit = totalSales
		.subtract(totalPurchaseOrders)
		.subtract(totalExpenses)
	        .subtract(totalPayroll)
	        .setScale(2, RoundingMode.HALF_UP);
	
	
	 String branchName = branchId != null
	                ? branchRepository.findById(branchId).map(Branch::getBranchName).orElse("—")
	                : "All Branches";
	
	
	 return new MonthlySummaryResponse(
	                month.toString(),
	                branchId,
	                branchName,
	                totalSales.setScale(2, RoundingMode.HALF_UP),
	                totalPurchaseOrders.setScale(2, RoundingMode.HALF_UP),
	                totalExpenses.setScale(2, RoundingMode.HALF_UP),
	                totalPayroll.setScale(2, RoundingMode.HALF_UP),
	                netProfit,
	                remittance.cashRemitted().setScale(2, RoundingMode.HALF_UP),
	                remittance.gcashRemitted().setScale(2, RoundingMode.HALF_UP),
	                remittance.totalRemitted().setScale(2, RoundingMode.HALF_UP));
	
	   
	   
    }

    @Override
    public List<DailyBreakdownEntry> dailyBreakdown(YearMonth month, Long branchId) {
	
	  LocalDate from = month.atDay(1);
	  LocalDate to = month.atEndOfMonth();
	  
	  List<SalesReport> sales = branchId != null
	            ? salesReportRepository.findByDateBetweenAndBranchId(from, to, branchId)
	            : salesReportRepository.findByDateBetween(from, to);
	  
	  
	  List<DailyExpense> expenses = branchId != null
	            ? dailyExpenseRepository.findByDateBetweenAndBranchId(from, to, branchId)
	            : dailyExpenseRepository.findByDateBetween(from, to);
	  
	  List<Attendance> attendance = branchId != null
	            ? attendanceRepository.findByDateBetweenAndBranchId(from, to, branchId)
	            : attendanceRepository.findByDateBetween(from, to);
	  
	  Map<LocalDate, BigDecimal> salesByDate = sales.stream()
		  .collect(Collectors.groupingBy(
			  SalesReport::getDate,
			  Collectors.reducing(BigDecimal.ZERO, SalesReport::getTotalSales, BigDecimal::add)));
		
	  
	  Map<LocalDate, BigDecimal> expensesByDate = expenses.stream()
	           .collect(Collectors.groupingBy(DailyExpense::getDate,
	                   Collectors.reducing(BigDecimal.ZERO, DailyExpense::getAmount, BigDecimal::add)));
	  
	  
	  Map<LocalDate, BigDecimal> payrollByDate = attendance.stream()
	            .collect(Collectors.groupingBy(Attendance::getDate,
	                    Collectors.reducing(BigDecimal.ZERO,
	                            a -> {
	                                Employee emp = employeeRepository.findById(a.getEmployeeId()).orElse(null);
	                                if (emp == null || emp.getHourlyRate() == null) return BigDecimal.ZERO;
	                                BigDecimal hours = hoursWorked(a.getTimeIn(), a.getTimeOut());
	                                return hours.multiply(emp.getHourlyRate());
	                            },
	                            BigDecimal::add)));
	  
	  
	  List<PurchaseOrderResponse> purchaseResponses =
	            purchaseOrderService.findForMonth(month, branchId);
	    Map<LocalDate, BigDecimal> purchasesByDate = purchaseResponses.stream()
	            .collect(Collectors.groupingBy(
	                    PurchaseOrderResponse::date,
	                    Collectors.reducing(BigDecimal.ZERO,
	                            PurchaseOrderResponse::totalCost,
	                            BigDecimal::add)));

	  
	    // Union of every date that has ANY activity
	    Set<LocalDate> allDates = new TreeSet<>();
	    allDates.addAll(salesByDate.keySet());
	    allDates.addAll(purchasesByDate.keySet());
	    allDates.addAll(expensesByDate.keySet());
	    allDates.addAll(payrollByDate.keySet());
	  
	    
	    return allDates.stream()
	            .map(date -> {
	                BigDecimal s = salesByDate.getOrDefault(date, BigDecimal.ZERO);
	                BigDecimal p = purchasesByDate.getOrDefault(date, BigDecimal.ZERO);
	                BigDecimal e = expensesByDate.getOrDefault(date, BigDecimal.ZERO);
	                BigDecimal pr = payrollByDate.getOrDefault(date, BigDecimal.ZERO)
	                        .setScale(2, RoundingMode.HALF_UP);
	                BigDecimal net = s.subtract(p).subtract(e).subtract(pr)
	                        .setScale(2, RoundingMode.HALF_UP);
	                return new DailyBreakdownEntry(date, s, p, e, pr, net);
	            })
	            .toList();
	    
	    
    }
    
    private BigDecimal hoursWorked(java.time.LocalTime timeIn, java.time.LocalTime timeOut) {
	    if (timeIn == null || timeOut == null) return BigDecimal.ZERO;
	    long minutes = java.time.Duration.between(timeIn, timeOut).toMinutes();
	    if (minutes < 0) minutes += 24 * 60; // overnight shift
	    return BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
	}

}
