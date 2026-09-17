package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.CashSummary;

public interface CashSummaryRepository extends JpaRepository <CashSummary, Long> {
    
    Optional<CashSummary> findByDateAndBranchId(LocalDate date, Long branchId);
    
    Optional<CashSummary> findFirstByBranchIdAndDateLessThanOrderByDateDesc(Long branchId, LocalDate date);
    
    List<CashSummary> findByDateBetween(LocalDate from, LocalDate to);

    List<CashSummary> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);

}
