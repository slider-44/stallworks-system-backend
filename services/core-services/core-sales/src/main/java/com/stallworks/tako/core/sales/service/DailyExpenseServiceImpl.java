package com.stallworks.tako.core.sales.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.sales.dto.DailyExpenseRequest;
import com.stallworks.tako.core.sales.dto.DailyExpenseResponse;
import com.stallworks.tako.core.sales.dto.ExpenseBatchRequest;
import com.stallworks.tako.core.sales.entity.DailyExpense;
import com.stallworks.tako.core.sales.mapper.ExpenseMapper;
import com.stallworks.tako.core.sales.repository.DailyExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyExpenseServiceImpl implements DailyExpenseService {

    private final DailyExpenseRepository dailyExpenseRepository;

    private final ExpenseMapper expenseMapper;

    public List<DailyExpenseResponse> createAll(ExpenseBatchRequest request) {

	// Flatten all expenses from the batch request
	List<DailyExpenseRequest> allExpenseRequests = request.expenses().stream().toList();

	// Convert requests to entities
	List<DailyExpense> entities = allExpenseRequests.stream()
	            .map(expenseMapper::toEntity)
	            .toList();
	    
	// Save and convert to response
	List<DailyExpense> savedEntities = dailyExpenseRepository.saveAll(entities);
	    
	 return savedEntities.stream()
	           .map(expenseMapper::toResponse)
	           .toList();
    }

    @Override
    public List<DailyExpenseResponse> findByDateAndBranch(LocalDate date, Long branchId) {
	
	return dailyExpenseRepository.findByDateAndBranchId(date, branchId)
		.stream()
		.map(expenseMapper::toResponse )
		.toList();
    }

    @Override
    public List<DailyExpenseResponse> findAll() {
	
	return null;
    }

    @Override
    public DailyExpenseResponse update(Long id, DailyExpenseRequest request) {
	DailyExpense expense = dailyExpenseRepository.findById(id)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

	expense.setBranchId(request.branchId());
	expense.setDate(request.date());
	expense.setDescription(request.description());
	expense.setAmount(request.amount());
	expense.setCategory(request.category());

	return expenseMapper.toResponse(dailyExpenseRepository.save(expense));
    }


    @Override
    public void delete(Long id) {
	if (!dailyExpenseRepository.existsById(id)) {
	    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
	}
	dailyExpenseRepository.deleteById(id);
    }
	
    
}
