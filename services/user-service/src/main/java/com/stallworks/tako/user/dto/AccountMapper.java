package com.stallworks.tako.user.dto;

import org.springframework.stereotype.Component;

import com.stallworks.tako.user.entity.Account;
import com.stallworks.tako.user.entity.Employee;

@Component
public class AccountMapper {

	public AccountResponse toResponse(Account account) {
		
		return AccountResponse.builder()
				.id(account.getId())
                .userName(account.getUserName())
                .enabled(account.isEnabled())
                .employee(toEmployeeSummary(account.getEmployee()))
                .build();
		
	}
	
	private EmployeeSummary toEmployeeSummary(Employee employee) {
		   return EmployeeSummary.builder()
	                .id(employee.getId())
	                .firstName(employee.getFirstName())
	                .lastName(employee.getLastName())
	                .role(employee.getRole())
	                .build();
	}
	
	public Account toEntity(AccountRequest request, Employee employee) {
		 return Account.builder()
	                .userName(request.userName())
	                .password(request.password())
	                .employee(employee)
	                .enabled(true)
	                .build();
		
	}
	

}
