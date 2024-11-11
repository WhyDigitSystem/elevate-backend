package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ReceiptReversalVO;

@Repository
public interface ReceiptReversalRepo extends JpaRepository<ReceiptReversalVO, Long> {

	@Query(nativeQuery = true, value = "select * from receiptreversal where orgid=?1")
	List<ReceiptReversalVO> getAllReceiptReversalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from receiptreversal where receiptreversalid=?1")
	List<ReceiptReversalVO> getAllReceiptReversalById(Long id);

	@Query(nativeQuery = true, value = "select * from receiptreversal where active=1")
	List<ReceiptReversalVO> findReceiptReversalByActive();

}
