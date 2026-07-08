package com.stallworks.tako.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.auth.dto.AccountMapper;
import com.stallworks.tako.auth.dto.AccountRequest;
import com.stallworks.tako.auth.dto.AccountResponse;
import com.stallworks.tako.auth.entity.Account;
import com.stallworks.tako.auth.repository.AccountRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final AccountRepository accountRepository;
	
	private final AccountMapper userMapper;

	
	

	@Override
	public AccountResponse createUser(AccountRequest request) {
		
		if(accountRepository.existsByUserName(request.userName())) {
			 throw new RuntimeException("Username already exists");
		}
		
		if(accountRepository.existsById(request.employeeId())) {
			 throw new RuntimeException("Employee already has account");
		}

		
		Account savedAccount = accountRepository.save(userMapper.toEntity(request));
		
		
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
