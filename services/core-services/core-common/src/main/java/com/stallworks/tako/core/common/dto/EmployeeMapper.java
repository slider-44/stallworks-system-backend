package com.stallworks.tako.core.common.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stallworks.tako.core.common.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {
	return Employee.builder()
		.firstName(request.firstName())
		.lastName(request.lastName())
		.phoneNumber(request.phoneNumber())
		.role(request.role())
	.build();
    }

    public EmployeeResponse toResponse(Employee employee, List<Long> branchIds) {
	return new EmployeeResponse(
		employee.getId(),
		employee.getFirstName(),
		employee.getLastName(),
		employee.getPhoneNumber(),
		employee.getRole(),
		employee.isActive(),
		branchIds);
    }

}
