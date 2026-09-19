package com.stallworks.tako.core.sales.mapper;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.sales.dto.SalaryAdvanceRequest;
import com.stallworks.tako.core.sales.dto.SalaryAdvanceResponse;
import com.stallworks.tako.core.sales.entity.SalaryAdvance;

@Component
public class SalaryAdvanceMapper {

    public SalaryAdvance toEntity(SalaryAdvanceRequest request) {
        return SalaryAdvance.builder()
                .employeeId(request.employeeId())
                .branchId(request.branchId())
                .date(request.date())
                .amount(request.amount())
                .note(request.note())
                .createdBy(request.recordedByEmployeeId())
                .build();
    }

    public SalaryAdvanceResponse toResponse(SalaryAdvance entity) {
        return new SalaryAdvanceResponse(
                entity.getId(),
                entity.getBranchId(),
                entity.getDate(),
                entity.getEmployeeId(),
                entity.getAmount(),
                entity.getNote(),
                entity.getCreatedBy());
    }
}