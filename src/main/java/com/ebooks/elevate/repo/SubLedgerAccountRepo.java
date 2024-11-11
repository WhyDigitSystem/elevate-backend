package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.SubLedgerAccountVO;

public interface SubLedgerAccountRepo extends JpaRepository<SubLedgerAccountVO, Long> {

	@Query(nativeQuery = true,value = "select * from subledgeraccount where subledgeraccountid=?1")
	List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id);

	@Query(nativeQuery = true,value = "select * from subledgeraccount where orgid=?1")
	List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from subledgeraccount where active=1")
	List<SubLedgerAccountVO> findSubLedgerAccountByActive();

	boolean existsByNewCodeAndOrgId(String newCode, Long orgId);

	boolean existsByOldCodeAndOrgId(String oldCode, Long orgId);

	boolean existsBySubLedgerNameAndOrgId(String subLedgerName, Long orgId);


}
