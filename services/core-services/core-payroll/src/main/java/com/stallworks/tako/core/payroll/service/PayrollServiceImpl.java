package com.stallworks.tako.core.payroll.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.attendance.entity.Attendance;
import com.stallworks.tako.core.attendance.repository.AttendanceRepository;
import com.stallworks.tako.core.common.entity.Branch;
import com.stallworks.tako.core.common.entity.Employee;
import com.stallworks.tako.core.common.repository.BranchRepository;
import com.stallworks.tako.core.common.repository.EmployeeRepository;
import com.stallworks.tako.core.payroll.dto.PayrollDailyEntryResponse;
import com.stallworks.tako.core.payroll.dto.PayrollDailyResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlyDetailResponse;
import com.stallworks.tako.core.payroll.dto.PayrollMonthlySummaryResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final AttendanceRepository attendanceRepository;
    
    private final EmployeeRepository employeeRepository;
    
    private final BranchRepository branchRepository;
    
    @Override
    public PayrollDailyResponse calculateDaily(Long employeeId, LocalDate date) {
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, date)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No attendance record for employee " + employeeId + " on " + date));

        Employee employee = requireEmployeeWithRate(employeeId);

        boolean shiftOpen = attendance.getTimeOut() == null;
        BigDecimal hours = hoursWorked(attendance.getTimeIn(), attendance.getTimeOut());
        BigDecimal dailySalary = hours.multiply(employee.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);

        return new PayrollDailyResponse(
                employeeId, date, attendance.getTimeIn(), attendance.getTimeOut(),
                shiftOpen, hours.setScale(2, RoundingMode.HALF_UP), employee.getHourlyRate(), dailySalary
        );
    }
    
    @Override
    public List<PayrollMonthlySummaryResponse> calculateMonthly(YearMonth month, Long branchId) {
        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();

        List<Attendance> records = branchId != null
                ? attendanceRepository.findByDateBetweenAndBranchId(from, to, branchId)
                : attendanceRepository.findByDateBetween(from, to);

        Map<Long, List<Attendance>> byEmployee = records.stream()
                .collect(Collectors.groupingBy(Attendance::getEmployeeId));

        return byEmployee.entrySet().stream()
                .map(entry -> summarize(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull) // no hourly rate set yet — skip rather than 500
                .sorted(Comparator.comparing(PayrollMonthlySummaryResponse::employeeName))
                .toList();
    }
    
    private PayrollMonthlySummaryResponse summarize(Long employeeId, List<Attendance> records) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null || employee.getHourlyRate() == null) {
            return null;
        }

        int daysWorked = records.size();
        BigDecimal totalHours = records.stream()
                .map(a -> hoursWorked(a.getTimeIn(), a.getTimeOut()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalEarned = totalHours.multiply(employee.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);

        // Branch shown per row = most recent record's branch. Under a branch
        // filter every record already shares that branch anyway; under "All
        // Branches" this is just a representative pick for display.
        Long branchId = records.stream()
                .max(Comparator.comparing(Attendance::getDate))
                .map(Attendance::getBranchId)
                .orElse(null);
        String branchName = branchId != null
                ? branchRepository.findById(branchId).map(Branch::getBranchName).orElse("—")
                : "—";

        return new PayrollMonthlySummaryResponse(
                employeeId,
                employee.getFirstName() + " " + employee.getLastName(),
                branchId,
                branchName,
                employee.getHourlyRate(),
                daysWorked,
                totalHours.setScale(2, RoundingMode.HALF_UP),
                totalEarned
        );
    }
    
    @Override
    public PayrollMonthlyDetailResponse monthlyDetail(Long employeeId, YearMonth month) {
        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();

        Employee employee = requireEmployeeWithRate(employeeId);

        List<Attendance> records = attendanceRepository
                .findByEmployeeIdAndDateBetweenOrderByDateDesc(employeeId, from, to);

        List<PayrollDailyEntryResponse> days = records.stream()
                .map(a -> {
                    BigDecimal hours = hoursWorked(a.getTimeIn(), a.getTimeOut());
                    BigDecimal earned = hours.multiply(employee.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);
                    return new PayrollDailyEntryResponse(
                            a.getDate(), a.getTimeIn(), a.getTimeOut(),
                            a.getTimeOut() == null, hours.setScale(2, RoundingMode.HALF_UP), earned
                    );
                })
                .toList();

        BigDecimal totalEarned = days.stream()
                .map(PayrollDailyEntryResponse::earned)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long branchId = records.stream()
                .max(Comparator.comparing(Attendance::getDate))
                .map(Attendance::getBranchId)
                .orElse(null);
        String branchName = branchId != null
                ? branchRepository.findById(branchId).map(Branch::getBranchName).orElse("—")
                : "—";

        return new PayrollMonthlyDetailResponse(
                employeeId,
                employee.getFirstName() + " " + employee.getLastName(),
                branchId,
                branchName,
                employee.getHourlyRate(),
                days,
                totalEarned
        );
    }
    
    private Employee requireEmployeeWithRate(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Employee not found: " + employeeId));
        if (employee.getHourlyRate() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Employee " + employeeId + " has no hourly rate set");
        }
        return employee;
    }
    
    private BigDecimal hoursWorked(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn == null) return BigDecimal.ZERO;
        LocalTime end = timeOut != null ? timeOut : LocalTime.now();
        long startMinutes = timeIn.getHour() * 60L + timeIn.getMinute();
        long endMinutes = end.getHour() * 60L + end.getMinute();
        long diff = endMinutes - startMinutes;
        if (diff < 0) diff += 24 * 60;
        return BigDecimal.valueOf(diff).divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
    }

    
   

}
