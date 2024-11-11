package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.DebitNoteVO;

public interface DebitNoteRepo extends JpaRepository<DebitNoteVO, Long> {

	@Query(nativeQuery = true, value = "select * from debitnote where orgid=?1")
	List<DebitNoteVO> getAllDebitNoteByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from debitnote where debitnoteid=?1")
	List<DebitNoteVO> getAllDebitNoteById(Long id);

	@Query(nativeQuery = true,value="select * from debitnote where active=1")
	List<DebitNoteVO> findDebitNoteByActive();

}
