package com.stallworks.tako.core.inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallworks.tako.core.inventory.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    List<PurchaseOrder> findByDateAndBranchId(LocalDate date, Long branchId);
    
    List<PurchaseOrder> findByDateBetween(LocalDate from, LocalDate to);
    
    List<PurchaseOrder> findByDateBetweenAndBranchId(LocalDate from, LocalDate to, Long branchId);
    

}
