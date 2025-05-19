package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.AutomationVO;

public interface AutomationRepo extends JpaRepository<AutomationVO, Long> {

	List<AutomationVO> findByOrgId(Long orgId);

}
