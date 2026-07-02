package com.stallworks.tako.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.auth.dto.AccountMapper;
import com.stallworks.tako.auth.dto.AccountRequest;
import com.stallworks.tako.auth.dto.AccountResponse;
import com.stallworks.tako.auth.entity.Account;
import com.stallworks.tako.auth.entity.Employee;
import com.stallworks.tako.auth.repository.AccountRepository;
import com.stallworks.tako.auth.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final AccountRepository userRepository;
	
	private final AccountMapper userMapper;
	
	private final EmployeeRepository employeeRepository;
	
	

	@Override
	public AccountResponse createUser(AccountRequest request) {
		
		if(userRepository.existsByUserName(request.userName())) {
			 throw new RuntimeException("Username already exists");
		}
		
		if(userRepository.existsById(request.employeeId())) {
			 throw new RuntimeException("Employee already has account");
		}
		
		Employee employee = employeeRepository.findById(request.employeeId())
				 .orElseThrow(() -> new RuntimeException("Employee not found"));
		
		Account savedAccount = userRepository.save(userMapper.toEntity(request, employee));
		
		
		return userMapper.toResponse(savedAccount);
		
		
	}

	@Override
	public List<AccountResponse> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountResponse getUser(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
