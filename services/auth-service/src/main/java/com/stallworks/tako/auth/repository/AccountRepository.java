package com.stallworks.tako.auth.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stallworks.tako.auth.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
    	Optional<Account> findByUserName(String userName);   // ← Must return Optional

    	boolean existsByEmployeeId(Long employeeId);
}