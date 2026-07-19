package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.DailyExpense;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, Long> {
	
	List<DailyExpense> findByDateAndBranchId(LocalDate date, Long branchId);

}
