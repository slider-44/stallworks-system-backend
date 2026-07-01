package com.stallworks.tako.user.dto;

import lombok.Builder;

@Builder
public record AccountResponse( 
		 Long id,
	        String userName,
	        boolean enabled,
	        EmployeeSummary employee ) {

}
