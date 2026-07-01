package com.stallworks.tako.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stallworks.tako.user.dto.AccountRequest;
import com.stallworks.tako.user.dto.AccountResponse;

public interface AccountService  {
	
	public AccountResponse createUser(AccountRequest user);
	public List<AccountResponse> getUsers();
	public AccountResponse getUser(Long Id);
	
	
}
