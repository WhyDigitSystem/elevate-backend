package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.CostCenterVO;

public interface CostCenterRepo extends JpaRepository<CostCenterVO, Long> {

	@Query(nativeQuery = true,value = "select * from costcenter where costcenterid=?1")
	List<CostCenterVO> getAllCostCenterById(Long id);

	@Query(nativeQuery = true,value = "select * from costcenter where orgid=?1")
	List<CostCenterVO> getAllCostCenterByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from costcenter where active=1")
	List<CostCenterVO> findCostCenterByActive();

	boolean existsByValueCodeAndOrgId(String valueCode, Long orgId);

	boolean existsByValueDescriptionAndOrgId(String valueDescription, Long orgId);

}
