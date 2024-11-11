package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.RolesResponsibilityVO;
import com.ebooks.elevate.entity.RolesVO;


public interface RolesResponsibilityRepo extends JpaRepository<RolesResponsibilityVO, Long>{

	List<RolesResponsibilityVO> findByRolesVO(RolesVO rolesVO);

}
