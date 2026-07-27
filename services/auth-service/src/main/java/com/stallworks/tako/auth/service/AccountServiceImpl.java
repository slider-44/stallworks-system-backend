package com.stallworks.tako.auth.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stallworks.tako.auth.client.EmployeeClient;
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

    private final PasswordEncoder passwordEncoder;
    
    private final EmployeeClient employeeClient;

    @Override
    public AccountResponse createUser(AccountRequest request) {

	if (!employeeClient.employeeExists(request.employeeId())) {
	    throw new RuntimeException("No such employee");
	}

	if (accountRepository.findByUserName(request.userName()).isPresent()) {
	    throw new RuntimeException("Username already exists");
	}

	if (accountRepository.existsByEmployeeId(request.employeeId())) {
	    throw new RuntimeException("Employee already has account");
	}

	Account account = userMapper.toEntity(request);
	account.setPassword(passwordEncoder.encode(account.getPassword()));

	Account savedAccount = accountRepository.save(account);

	return userMapper.toResponse(savedAccount);

    }

    @Override
    public List<AccountResponse> getUsers() {

	return accountRepository.findAll().stream()
		.map(userMapper::toResponse)
		.toList();

    }

    @Override
    public AccountResponse getUser(Long Id) {
	// TODO Auto-generated method stub
	return null;
    }

}
