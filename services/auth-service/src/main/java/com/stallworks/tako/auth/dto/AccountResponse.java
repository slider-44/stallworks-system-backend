package com.stallworks.tako.auth.dto;

import lombok.Builder;

@Builder
public record AccountResponse( 
		 Long id,
	        String userName,
	        boolean enabled,
	        Long employeeId) {

}
