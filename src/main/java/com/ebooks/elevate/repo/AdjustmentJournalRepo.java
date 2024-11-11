package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.AdjustmentJournalVO;

@Repository
public interface AdjustmentJournalRepo extends JpaRepository<AdjustmentJournalVO, Long> {

	@Query(nativeQuery = true, value = "select * from adjustmentjournal where orgid=?1")
	List<AdjustmentJournalVO> getAllAdjustmentJournalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from adjustmentjournal where adjustmentjournalid=?1")
	List<AdjustmentJournalVO> getAdjustmentJournalById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getAdjustmentJournalDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getAdjustmentJournalByDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
