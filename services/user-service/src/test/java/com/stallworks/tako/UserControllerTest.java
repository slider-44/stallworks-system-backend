package com.stallworks.tako;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.stallworks.tako.user.controller.UserController;
import com.stallworks.tako.user.dto.AccountRequest;
import com.stallworks.tako.user.dto.AccountResponse;
import com.stallworks.tako.user.dto.EmployeeSummary;
import com.stallworks.tako.user.service.AccountService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private AccountService userService;

	@Test
	public void createUser_shouldReturn201WithLocationAndBody() throws Exception {

		
		EmployeeSummary employee = EmployeeSummary.builder()
		        .id(1L)
		        .firstName("Jane")
		        .lastName("Doe")
		        .build();

		
		AccountRequest request = AccountRequest.builder()
				.employeeId(1L)
				.userName("jdoe")
				.password("secret123")
				.build();
		
				
		AccountResponse	response = AccountResponse.builder()
				.id(1L)
				.userName("jdoe")
				.enabled(true)
				.employee(employee)
				.build();


		given(userService.createUser(any(AccountRequest.class))).willReturn(response);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", endsWith("/users/1")))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.userName").value("jdoe"))
				.andExpect(jsonPath("$.employee.firstName").value("Jane"))
				.andExpect(jsonPath("$.employee.lastName").value("Doe"));

	}

}
