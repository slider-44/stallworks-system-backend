package com.stallworks.tako.core.sales.repository;

import com.stallworks.tako.core.sales.TestConfig;
import com.stallworks.tako.core.sales.entity.SalesReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = TestConfig.class)
class SalesReportRepositoryTest {

    @Autowired
    private SalesReportRepository repository;

    @Test
    void findByDateAndBranchId_returnsReport_whenExists() {
        repository.save(SalesReport.builder()
                .employeeId(1L)
                .branchId(5L)
                .date(LocalDate.of(2026, 7, 12))
                .timeIn(LocalTime.of(8, 0))
                .timeOut(LocalTime.of(17, 0))
                .totalSales(new BigDecimal("245.00"))
                .build());

        Optional<SalesReport> result = repository.findByDateAndBranchId(
                LocalDate.of(2026, 7, 12), 5L);

        assertThat(result).isPresent();
        assertThat(result.get().getBranchId()).isEqualTo(5L);
    }
}