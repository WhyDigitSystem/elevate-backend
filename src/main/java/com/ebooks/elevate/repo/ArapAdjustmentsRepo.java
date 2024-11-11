package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ArapAdjustmentsVO;

@Repository
public interface ArapAdjustmentsRepo extends JpaRepository<ArapAdjustmentsVO, Long> {

	@Query(nativeQuery = true, value = "select * from arapadjustments where orgid=?1")
	List<ArapAdjustmentsVO> getAllArapAdjustmentsByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from arapadjustments where arapadjustmentsid=?1")
	List<ArapAdjustmentsVO> getAllArapAdjustmentsById(Long id);
	@Query(nativeQuery = true, value = "select * from arapadjustments where active=1")
	
	List<ArapAdjustmentsVO> findArapAdjustmentsByActive();

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getArapAdjustmentsDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(value = "select a from ArapAdjustmentsVO a where a.id=?1")
	ArapAdjustmentsVO findArapAdjustmentsByDocId(Long orgId, String docId);

}
