package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ChartCostCenterVO;

public interface ChartCostCenterRepo extends JpaRepository<ChartCostCenterVO, Long> {

	@Query(nativeQuery = true, value = "select * from chartcostcenter where orgid=?1")
	List<ChartCostCenterVO> getAllChartCostCenterByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from chartcostcenter where chartcostcenterid=?1")
	List<ChartCostCenterVO> getChartCostCenterById(Long id);       
 
	@Query(nativeQuery = true, value = "select * from chartcostcenter where active=1")
	List<ChartCostCenterVO> findChartCostCenterByActive();

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPartyMasterDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getChartCostCenterDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
