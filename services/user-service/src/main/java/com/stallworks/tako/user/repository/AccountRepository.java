package com.stallworks.tako.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stallworks.tako.user.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByUserName(String userName);
	boolean existsByUserName(String userName);
	
	@Query("SELECT a FROM Account a JOIN FETCH a.employee")
    List<Account> findAllWithEmployee();
	

}
