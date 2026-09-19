package com.stallworks.tako.core.sales.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stallworks.tako.core.sales.dto.SalaryAdvanceRequest;
import com.stallworks.tako.core.sales.dto.SalaryAdvanceResponse;
import com.stallworks.tako.core.sales.entity.SalaryAdvance;
import com.stallworks.tako.core.sales.mapper.SalaryAdvanceMapper;
import com.stallworks.tako.core.sales.repository.SalaryAdvanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryAdvanceServiceImpl implements SalaryAdvanceService {
    
    private final SalaryAdvanceRepository salaryAdvanceRepository;

    private final SalaryAdvanceMapper salaryAdvanceMapper;

    @Override
    public SalaryAdvanceResponse create(SalaryAdvanceRequest request) {
	  SalaryAdvance saved = salaryAdvanceRepository.save(salaryAdvanceMapper.toEntity(request));
	  return salaryAdvanceMapper.toResponse(saved);
    }

    @Override
    public List<SalaryAdvanceResponse> findByDateAndBranch(LocalDate date, Long branchId) {
        return salaryAdvanceRepository.findByDateAndBranchId(date, branchId)
                .stream()
                .map(salaryAdvanceMapper::toResponse)
                .toList();
    }

    @Override
    public SalaryAdvanceResponse update(Long id, SalaryAdvanceRequest request) {
        SalaryAdvance advance = salaryAdvanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary advance not found"));

        advance.setEmployeeId(request.employeeId());
        advance.setBranchId(request.branchId());
        advance.setDate(request.date());
        advance.setAmount(request.amount());
        advance.setNote(request.note());

        return salaryAdvanceMapper.toResponse(salaryAdvanceRepository.save(advance));
    }


    @Override
    public void delete(Long id) {
        if (!salaryAdvanceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary advance not found");
        }
        salaryAdvanceRepository.deleteById(id);
    }

    @Override
    public BigDecimal sumForDateAndBranch(LocalDate date, Long branchId) {
        return findByDateAndBranch(date, branchId).stream()
                .map(SalaryAdvanceResponse::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
