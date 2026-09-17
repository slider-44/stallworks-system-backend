package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.sales.dto.OverheadExpenseRequest;
import com.stallworks.tako.core.sales.dto.OverheadExpenseResponse;
import com.stallworks.tako.core.sales.entity.OverheadExpense;
import com.stallworks.tako.core.sales.mapper.OverheadExpenseMapper;
import com.stallworks.tako.core.sales.repository.OverheadExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OverheadExpenseServiceImpl implements OverheadExpenseService {
    
    private final OverheadExpenseRepository overheadExpenseRepository;

    private final OverheadExpenseMapper overheadExpenseMapper;

    @Override
    public OverheadExpenseResponse create(OverheadExpenseRequest request) {
	
	OverheadExpense saved = overheadExpenseRepository.save(overheadExpenseMapper.toEntity(request));
	
	return overheadExpenseMapper.toResponse(saved);
    }

    @Override
    public List<OverheadExpenseResponse> findForMonth(YearMonth month, Long branchId) {
	
	LocalDate from = month.atDay(1);
	LocalDate to = month.atEndOfMonth();
	
	List<OverheadExpense> records = branchId != null
	             ? overheadExpenseRepository.findByDateBetweenAndBranchId(from, to, branchId)
	             : overheadExpenseRepository.findByDateBetween(from, to);
	   
	return records.stream()
		.map(overheadExpenseMapper:: toResponse)
		.toList();

	
    }

    @Override
    public OverheadExpenseResponse update(Long id, OverheadExpenseRequest request) {
	
	  OverheadExpense expense = overheadExpenseRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Overhead expense not found"));
	  
	  
	  expense.setBranchId(request.branchId());
	  expense.setDate(request.date());
	  expense.setDescription(request.description());
	  expense.setAmount(request.amount());
	  
	  return overheadExpenseMapper.toResponse(overheadExpenseRepository.save(expense));

	
    }

    @Override
    public void delete(Long id) {
	if (!overheadExpenseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Overhead expense not found");
        }
        overheadExpenseRepository.deleteById(id);
	
    }

    @Override
    public BigDecimal sumForMonth(YearMonth month, Long branchId) {
	
	return findForMonth(month, branchId).stream()
		.map(OverheadExpenseResponse :: amount)
		 .reduce(BigDecimal.ZERO, BigDecimal::add);
	
    }

}
