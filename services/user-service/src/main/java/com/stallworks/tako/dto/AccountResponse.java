package com.stallworks.tako.dto;

import lombok.Builder;

@Builder
public record AccountResponse( 
		 Long id,
	        String userName,
	        boolean enabled,
	        EmployeeSummary employee ) {

}
