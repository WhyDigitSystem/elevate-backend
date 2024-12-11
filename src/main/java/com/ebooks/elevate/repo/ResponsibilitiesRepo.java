package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ResponsibilityVO;

public interface ResponsibilitiesRepo extends JpaRepository<ResponsibilityVO, Long> {

	boolean existsByResponsibilityAndOrgId(String responsibility,Long OrgId);

	
//	@Query(value="select a from ResponsibilityVO a where a.orgId=?1 ")
//	List<ResponsibilityVO> findAllResponsibilityByOrgId(Long orgId);

	
//	List<ResponsibilityVO> findAllActiveResponsibilityByOrgId();

	@Query(value="select a.id,a.responsibility from ResponsibilityVO a where  a.active=true")
	Set<Object[]> findActiveResponsibility();

	@Query(value="select a from ResponsibilityVO a where a.active=true ")
	List<ResponsibilityVO> findAllActiveResponsibility();


}
