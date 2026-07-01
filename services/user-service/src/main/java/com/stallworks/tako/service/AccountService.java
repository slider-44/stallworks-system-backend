package com.stallworks.tako.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.dto.AccountRequest;
import com.stallworks.tako.dto.AccountResponse;

public interface AccountService  {
	
	public AccountResponse createUser(AccountRequest user);
	public List<AccountResponse> getUsers();
	public AccountResponse getUser(Long Id);
	
	
}
