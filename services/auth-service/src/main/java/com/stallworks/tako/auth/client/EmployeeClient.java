package com.stallworks.tako.auth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.stallworks.tako.auth.security.JwtService;

@Component
public class EmployeeClient {

    private final RestClient restClient;
    private final JwtService jwtService;

    public EmployeeClient(@Value("${core-service.base-url}") String baseUrl, JwtService jwtService) {
	this.restClient = RestClient.builder().baseUrl(baseUrl).build();
	this.jwtService = jwtService;
    }

    public boolean employeeExists(Long employeeId) {
	try {

	    restClient.get()
	    .uri("/api/v1/employees/{id}", employeeId)
	    .header("Authorization", "Bearer " + jwtService.generateServiceToken())
            .retrieve()
            .toBodilessEntity();

	    return true;

	} catch (HttpClientErrorException.NotFound e) {
            return false;
        }

    }
}
