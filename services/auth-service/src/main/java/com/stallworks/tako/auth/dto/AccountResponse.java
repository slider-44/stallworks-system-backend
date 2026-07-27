package com.stallworks.tako.auth.dto;

import java.time.LocalDateTime;

import com.stallworks.tako.auth.entity.Role;

import lombok.Builder;

@Builder
public record AccountResponse( 
		 Long id,
	        String userName,
	        boolean enabled,
	        Role role,
	        Long employeeId,
	        LocalDateTime createdAt) {

}
