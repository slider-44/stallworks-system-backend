package com.stallworks.tako.core.sales;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;


@Configuration
@EntityScan(basePackages = {
    "com.stallworks.tako.core.sales.entity",
    "com.stallworks.tako.core.inventory.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.stallworks.tako.core.sales.repository",
    "com.stallworks.tako.core.inventory.repository"
})
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestConfig {
}