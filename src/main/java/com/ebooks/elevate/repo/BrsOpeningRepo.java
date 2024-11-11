package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.BrsOpeningVO;

public interface BrsOpeningRepo extends JpaRepository<BrsOpeningVO, Long> {

	@Query(nativeQuery = true,value = "Select * from brsopening where orgid=?1")
	List<BrsOpeningVO> getAllBrsOpeningByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from brsopening where brsopeningid=?1")
	List<BrsOpeningVO> getAllBrsOpeningById(Long id);

	@Query(nativeQuery = true,value = "select * from brsopening where active=1")
	List<BrsOpeningVO> findBrsOpeningByActive();

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBrsOpeningDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
