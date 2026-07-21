package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.sales.dto.BillCountLineRequest;
import com.stallworks.tako.core.sales.dto.CashSummaryRequest;
import com.stallworks.tako.core.sales.dto.CashSummaryResponse;
import com.stallworks.tako.core.sales.entity.BillCountLine;
import com.stallworks.tako.core.sales.entity.CashSummary;
import com.stallworks.tako.core.sales.entity.CloseShiftRequest;
import com.stallworks.tako.core.sales.mapper.CashSummaryMapper;
import com.stallworks.tako.core.sales.repository.CashSummaryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashSummaryServiceImpl implements CashSummaryService {
    
    
    private final CashSummaryRepository cashSummaryRepository;
    
    private final CashSummaryMapper cashSummaryMapper;
    
    private static final ZoneId MANILA = ZoneId.of("Asia/Manila");
    
    private record RemittanceTotals(
            BigDecimal actualCash,
            BigDecimal cashRemittance,
            BigDecimal totalRemittance
    ) {}


    @Override
    @Transactional
    public CashSummaryResponse save(CashSummaryRequest request) {
	
	  CashSummary summary = cashSummaryRepository
	            .findByDateAndBranchId(request.date(), request.branchId())
	            .orElseGet(CashSummary::new);
	  
	  boolean isNew = summary.getId() == null;
	  
	  
	  summary.setDate(request.date());
	  summary.setBranchId(request.branchId());
	  summary.setPettyCashYesterday(request.pettyCashYesterday());
	  summary.setGcash(request.gcash());
	  summary.setPettyCashNextday(request.pettyCashNextday());
	  
	  if (isNew) {
	        summary.setCreatedBy(request.actorEmployeeId());
	  }
	  
	  summary.setUpdatedBy(request.actorEmployeeId());
	  
	  summary.getBillCounts().clear();
	  
	  for (BillCountLineRequest line : request.billCounts()) {
	        BillCountLine bcl = BillCountLine.builder()
	                .denomination(line.denomination())
	                .count(line.count())
	                .build();
	        bcl.setCashSummary(summary);
	        summary.getBillCounts().add(bcl);
	    }
	
	
	  CashSummary saved = cashSummaryRepository.save(summary);
	  return toResponseWithTotals(saved);
	  
    }
    
    private CashSummaryResponse toResponseWithTotals(CashSummary summary) {
        RemittanceTotals totals = calculateRemittanceTotals(summary);
        return cashSummaryMapper.toResponse(
                summary, totals.actualCash(), totals.cashRemittance(), totals.totalRemittance());
    }
    
    private RemittanceTotals calculateRemittanceTotals(CashSummary summary) {
        BigDecimal actualCash = summary.getBillCounts().stream()
                .map(line -> BigDecimal.valueOf(line.getDenomination()).multiply(BigDecimal.valueOf(line.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pettyCashNextday = summary.getPettyCashNextday() != null ? summary.getPettyCashNextday() : BigDecimal.ZERO;
        BigDecimal gcash = summary.getGcash() != null ? summary.getGcash() : BigDecimal.ZERO;

        BigDecimal cashRemittance = actualCash.subtract(pettyCashNextday);
        BigDecimal totalRemittance = cashRemittance.add(gcash);

        return new RemittanceTotals(actualCash, cashRemittance, totalRemittance);
    }

    @Override
    public Optional<CashSummaryResponse> findByDateAndBranch(LocalDate date, Long branchId) {
        return cashSummaryRepository.findByDateAndBranchId(date, branchId)
                .map(this::toResponseWithTotals);
    }
    
    public CashSummaryResponse closeShift(CloseShiftRequest request) {
	    CashSummary summary = cashSummaryRepository.findByDateAndBranchId(request.date(), request.branchId())
	            .orElseThrow(() -> new IllegalStateException("Save Cash Count before closing the shift"));
	    summary.setClosed(true);
	    summary.setClosedAt(LocalDateTime.now(MANILA));
	    summary.setClosedBy(request.employeeId());
	    return toResponseWithTotals(cashSummaryRepository.save(summary));
	}

}
