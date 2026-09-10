package com.stallworks.tako.core.common.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	       if (request.phoneNumber() != null && !request.phoneNumber().isBlank()
		        && employeeRepository.existsByPhoneNumber(request.phoneNumber())) {
		     throw new RuntimeException("Employee already exists");
		}
		
		Employee savedEmp =  employeeRepository.save(employeeMapper.toEntity(request));
		
		List<EmployeeBranch> branchesToSave = request.branchIds().stream()
		        .map(branchId -> EmployeeBranch.builder()
		                .employee(savedEmp)
		                .branch(branchRepository.getReferenceById(branchId))
		                .build())
		        .toList();

		

		
		employeeBranchRepository.saveAll(branchesToSave);
		
	
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

	@Override
	public EmployeeResponse getById(Long id) {
	  
	    Employee emp = employeeRepository.findById(id)
		    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
	    
	    List<Long> branchIds = employeeBranchRepository.findAllByEmployeeIdsWithEmployee(List.of(id)).stream()
		        .map(eb -> eb.getBranch().getId())
		        .toList();
	    
	    return employeeMapper.toResponse(emp, branchIds);

	}

	@Override
	public EmployeeResponse update(Long id, EmployeeRequest request) {
	    
	    Employee employee = employeeRepository.findById(id)
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
	    
	    employee.setFirstName(request.firstName());
	    employee.setLastName(request.lastName());
	    employee.setPhoneNumber(request.phoneNumber());
	    employee.setRole(request.role());
	    employee.setHourlyRate(request.hourlyRate());
	    
	    Employee saved = employeeRepository.save(employee);
	    
	    employeeBranchRepository.deleteAllByEmployeeId(id);
	    
	    List<EmployeeBranch> branchesToSave = request.branchIds().stream()
	            .map(branchId -> EmployeeBranch.builder()
	                    .employee(saved)
	                    .branch(branchRepository.getReferenceById(branchId))
	                    .build())
	            .toList();
	    
	    employeeBranchRepository.saveAll(branchesToSave);

	    return employeeMapper.toResponse(
	            saved,
	            branchesToSave.stream()
	            .map(EmployeeBranch::getBranch)
	            .map(Branch::getId)
	            .toList()
	    );


	}

}
