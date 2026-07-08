package com.stallworks.tako.auth.dto;

import org.springframework.stereotype.Component;

import com.stallworks.tako.auth.entity.Account;

@Component
public class AccountMapper {

	public AccountResponse toResponse(Account account) {
		
		return AccountResponse.builder()
				.id(account.getId())
                .userName(account.getUserName())
                .enabled(account.isEnabled())
                .employeeId(account.getEmployeeId())
                .build();
		
	}
	

	
	public Account toEntity(AccountRequest request) {
		 return Account.builder()
	                .userName(request.userName())
	                .password(request.password())
	                .employeeId(request.employeeId())
	                .enabled(true)
	                .build();
		
	}
	

}
