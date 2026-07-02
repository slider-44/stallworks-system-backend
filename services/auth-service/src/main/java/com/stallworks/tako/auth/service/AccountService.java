package com.stallworks.tako.auth.service;

import java.util.List;



import com.stallworks.tako.auth.dto.AccountRequest;
import com.stallworks.tako.auth.dto.AccountResponse;

public interface AccountService  {
	
	public AccountResponse createUser(AccountRequest user);
	public List<AccountResponse> getUsers();
	public AccountResponse getUser(Long Id);
	
	
}
