package com.stallworks.tako.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stallworks.tako.auth.entity.EmployeeBranch;

@Repository
public interface EmployeeBranchRepository extends JpaRepository<EmployeeBranch, Long> {

	boolean existsByEmployeeIdAndBranchId(Long employeeId, Long branchId);

	@Query("""
			SELECT eb
			FROM EmployeeBranch eb
			JOIN FETCH eb.employee
			WHERE eb.branchId = :branchId
			""")
	List<EmployeeBranch> findAllByBranchIdWithEmployee(Long branchId);
	
	@Query("""
	        SELECT eb
	        FROM EmployeeBranch eb
	        JOIN FETCH eb.employee
	        WHERE eb.employee.id IN :employeeIds
	        """)
	List<EmployeeBranch> findByEmployeeIdIn(List<Long> employeeIds);

}
