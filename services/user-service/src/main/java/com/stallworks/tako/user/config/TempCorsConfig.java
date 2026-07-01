package com.stallworks.tako.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO: remove once the API Gateway is in place and handles CORS centrally.

@Configuration
public class TempCorsConfig implements WebMvcConfigurer {
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/v1/**")
	                .allowedOrigins("http://localhost:5173") 
	                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
	                .allowedHeaders("*")
	                .allowCredentials(true)
	                .maxAge(3600);
	    }

}
