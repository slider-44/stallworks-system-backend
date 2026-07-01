package com.stallworks.tako.user.dto;

import java.util.List;


public record EmployeeResponse( 
		Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        Role role,
        boolean active,
        List<Long> branchIds) {

}
