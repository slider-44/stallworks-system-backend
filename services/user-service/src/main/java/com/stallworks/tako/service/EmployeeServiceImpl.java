package com.stallworks.tako.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stallworks.tako.dto.EmployeeMapper;
import com.stallworks.tako.dto.EmployeeRequest;
import com.stallworks.tako.dto.EmployeeResponse;
import com.stallworks.tako.entity.Employee;
import com.stallworks.tako.entity.EmployeeBranch;
import com.stallworks.tako.repository.EmployeeBranchRepository;
import com.stallworks.tako.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	
	private final EmployeeBranchRepository employeeBranchRepository;
	
	private final EmployeeMapper employeeMapper;
	
	@Override
	@Transactional
	public EmployeeResponse create(EmployeeRequest request) {
		
		// 1. Check if employee already exists
		if(employeeRepository.existsByPhoneNumber(request.phoneNumber())) {
			 throw new RuntimeException("Employee already exists");
		}
		
		Employee savedEmp =  employeeRepository.save(employeeMapper.toEntity(request));

		
		List<EmployeeBranch> branchesToSave = request.branchIds().stream()
				.map(branchId -> EmployeeBranch.builder()
						.employee(savedEmp)
						.branchId(branchId)
						.build())
				.toList();
		
		employeeBranchRepository.saveAll(branchesToSave);
		
		return employeeMapper.toResponse(savedEmp, branchesToSave.stream()
				.map(EmployeeBranch::getBranchId).toList());

				
	}

	@Override
	public List<EmployeeResponse> getAll() {
		 List<Employee> employees = employeeRepository.findAll();
		 
		 List<Long> employeeIds = employees.stream()
				 .map(Employee::getId)
				 .toList();
		 
		 List<EmployeeBranch> employeeBranches = employeeBranchRepository.findByEmployeeIdIn(employeeIds);
		 
		// Group branch ids by employee id, e.g. { 1 -> [1,2], 2 -> [3] }
         Map<Long, List<Long>> branchIdsByEmployeeId = employeeBranches.stream()
                .collect(Collectors.groupingBy(
                        eb -> eb.getEmployee().getId(),
                        Collectors.mapping(EmployeeBranch::getBranchId, Collectors.toList())
                ));
         
         

         return employees.stream()
                 .map(emp -> employeeMapper.toResponse(
                         emp,
                         branchIdsByEmployeeId.getOrDefault(emp.getId(), List.of())
                 ))
                 .toList();
        
	}

}
