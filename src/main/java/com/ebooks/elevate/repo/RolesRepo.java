package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.RolesVO;

public interface RolesRepo extends JpaRepository<com.ebooks.elevate.entity.RolesVO, Long> {

	//boolean existsByRoleAndOrgId(String role, Long orgId);

	
//	List<RolesVO> findAllActiveRolesByOrgId(Long orgId);

	@Query(value = "select a from RolesVO a where a.active=true and a.orgId=?1 ")
	List<RolesVO> findAllActiveRoles(Long orgId);

	@Query(value = "select a from RolesVO a where a.orgId=?1")
	List<RolesVO> findAllByOrgId(Long orgId);

//	@Query(value = "select a from RolesVO a where  a.orgId=?1")
//	List<RolesVO> findAllRolesByOrgId(Long orgId);

}
