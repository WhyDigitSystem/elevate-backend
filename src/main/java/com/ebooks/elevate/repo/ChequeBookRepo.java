package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ChequeBookVO;

public interface ChequeBookRepo extends JpaRepository<ChequeBookVO, Long> {

	@Query(nativeQuery = true, value = "select * from chequebook where chequebookid=?1")
	List<ChequeBookVO> getAllChequeBookById(Long id);

	@Query(nativeQuery = true, value = "select * from chequebook where orgid=?1")
	List<ChequeBookVO> getAllChequeBookByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from chequebook where active=1")
	List<ChequeBookVO> findChequeBookByActive();

}
