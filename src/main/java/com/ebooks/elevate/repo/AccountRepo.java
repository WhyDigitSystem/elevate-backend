package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.AccountVO;

public interface AccountRepo extends JpaRepository<AccountVO, Long>{
@Query(value = "select * from account where orgid=?1",nativeQuery = true)
	List<AccountVO> getAllAccountByOrgId(Long orgId);

@Query(value = "select * from account where accountid=?1",nativeQuery = true)
List<AccountVO> getAllAccountById(Long accountId);

@Query(nativeQuery = true,value = "select * from account where active=1")
List<AccountVO> findAccountByActive();

boolean existsByAccountNameAndOrgId(String accountName, Long orgId);

boolean existsByAccountCodeAndOrgId(String accountCode, Long orgId);



}
