package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.OverheadExpense;

public interface OverheadExpenseRepository extends JpaRepository<OverheadExpense, Long> {
    
    List<OverheadExpense> findByDateBetween(LocalDate from, LocalDate to);
    
    List<OverheadExpense> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);


}
