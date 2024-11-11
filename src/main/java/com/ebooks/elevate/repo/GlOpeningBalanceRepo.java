package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.GlOpeningBalanceVO;
@Repository
public interface GlOpeningBalanceRepo extends JpaRepository<GlOpeningBalanceVO,Long> {
	@Query(nativeQuery = true, value = "select * from glopeningbalance where orgid=?1")
	List<GlOpeningBalanceVO> getAllGlOpeningBalanceByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from glopeningbalance where glopeningbalanceid=?1")
	List<GlOpeningBalanceVO> getAllGlOpeningBalanceById(Long id);

	@Query(nativeQuery = true, value = "select * from glopeningbalance where active=1")
	List<GlOpeningBalanceVO> findGlOpeningBalanceByActive();

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getGlOpeningBalanceDocId(Long orgId, String finYear, String branchCode, String screenCode);
    
	@Query(nativeQuery = true, value = "select * from glopeningbalance where orgid=?1 and docid=?2")
	GlOpeningBalanceVO findAllGlOpeningBalanceByDocId(Long orgId, String docId);
}
