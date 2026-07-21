package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.CashSummary;

public interface CashSummaryRepository extends JpaRepository <CashSummary, Long> {
    
    Optional<CashSummary> findByDateAndBranchId(LocalDate date, Long branchId);
}
