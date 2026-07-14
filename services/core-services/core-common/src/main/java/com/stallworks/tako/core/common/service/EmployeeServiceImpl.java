package com.stallworks.tako.core.common.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stallworks.tako.core.common.dto.EmployeeMapper;
import com.stallworks.tako.core.common.dto.EmployeeRequest;
import com.stallworks.tako.core.common.dto.EmployeeResponse;
import com.stallworks.tako.core.common.entity.Branch;
import com.stallworks.tako.core.common.entity.Employee;
import com.stallworks.tako.core.common.entity.EmployeeBranch;
import com.stallworks.tako.core.common.repository.BranchRepository;
import com.stallworks.tako.core.common.repository.EmployeeBranchRepository;
import com.stallworks.tako.core.common.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	
	private final EmployeeBranchRepository employeeBranchRepository;
	
	private final EmployeeMapper employeeMapper;
	
	private final BranchRepository branchRepository;
	
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
		                .branch(branchRepository.getReferenceById(branchId))
		                .build())
		        .toList();

		
//		List<EmployeeBranch> branchesToSave = request.branchIds().stream()
//				.map(branchId -> EmployeeBranch.builder()
//						.employee(savedEmp)
//						.branchId(branchId)
//						.build())
//				.toList();
		
		employeeBranchRepository.saveAll(branchesToSave);
		
//		return employeeMapper.toResponse(savedEmp, branchesToSave.stream()
//				.map(EmployeeBranch::getBranchId).toList());
		
		return employeeMapper.toResponse(
		        savedEmp,
		        branchesToSave.stream()
		                .map(EmployeeBranch::getBranch)
		                .map(Branch::getId)
		                .toList()
		);

				
	}

	@Override
	public List<EmployeeResponse> getAll() {
		 List<Employee> employees = employeeRepository.findAll();
		 
		 List<Long> employeeIds = employees.stream()
				 .map(Employee::getId)
				 .toList();
		 
		 List<EmployeeBranch> employeeBranches = employeeBranchRepository.findAllByEmployeeIdsWithEmployee(employeeIds);
		 
		// Group branch ids by employee id, e.g. { 1 -> [1,2], 2 -> [3] }
//         Map<Long, List<Long>> branchIdsByEmployeeId = employeeBranches.stream()
//                .collect(Collectors.groupingBy(
//                        eb -> eb.getEmployee().getId(),
//                        Collectors.mapping(EmployeeBranch::getBranchId, Collectors.toList())
//                ));
         
		 
		 Map<Long, List<Long>> branchIdsByEmployeeId = employeeBranches.stream()
			        .collect(Collectors.groupingBy(
			                eb -> eb.getEmployee().getId(),
			                Collectors.mapping(
			                        eb -> eb.getBranch().getId(),
			                        Collectors.toList()
			                )
			        ));
         

         return employees.stream()
                 .map(emp -> employeeMapper.toResponse(
                         emp,
                         branchIdsByEmployeeId.getOrDefault(emp.getId(), List.of())
                 ))
                 .toList();
        
	}

}
