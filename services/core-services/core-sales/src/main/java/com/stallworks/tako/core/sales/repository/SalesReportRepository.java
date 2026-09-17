package com.stallworks.tako.core.sales.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.sales.entity.SalesReport;

public interface SalesReportRepository extends JpaRepository<SalesReport, Long>{
	
	Optional<SalesReport> findByDateAndBranchId(LocalDate date, Long branchId);
	
	List<SalesReport> findByDateBetween(LocalDate from, LocalDate to);
	
	List<SalesReport> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);
	
	
}
