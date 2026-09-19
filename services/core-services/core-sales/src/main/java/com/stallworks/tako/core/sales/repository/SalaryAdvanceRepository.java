package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.SalaryAdvance;

public interface SalaryAdvanceRepository extends JpaRepository<SalaryAdvance, Long> {
   
    List<SalaryAdvance> findByDateAndBranchId(LocalDate date, Long branchId);

    List<SalaryAdvance> findByDateBetween(LocalDate from, LocalDate to);

    List<SalaryAdvance> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);

    List<SalaryAdvance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate from, LocalDate to);

}
