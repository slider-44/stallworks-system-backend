package com.stallworks.tako.core.sales.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cash_summaries",
uniqueConstraints = @UniqueConstraint(columnNames = {"date", "branch_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CashSummary {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false) 
    private LocalDate date;
    
    @Column(nullable = false) 
    private Long branchId;
    
    private BigDecimal pettyCashYesterday; // feeds Expected Cash on Sales & Expenses
    private BigDecimal gcash;              // GCash received this shift
    private BigDecimal pettyCashNextday;   // float kept aside — "Starting Float" in the UI
    
    @OneToMany(mappedBy = "cashSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BillCountLine> billCounts = new ArrayList<>();
    
    @CreationTimestamp 
    private LocalDateTime createdAt;
    
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    
    @UpdateTimestamp 
    private LocalDateTime updatedAt;
    
    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean closed = false;

    private LocalDateTime closedAt;
    private Long closedBy;
    

}
