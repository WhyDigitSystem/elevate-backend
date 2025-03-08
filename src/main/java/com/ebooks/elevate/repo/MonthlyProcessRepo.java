package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.MonthlyProcessVO;

public interface MonthlyProcessRepo extends JpaRepository<MonthlyProcessVO, Long> {

	List<MonthlyProcessVO> findByOrgIdAndClientCode(Long orgId, String clientCode);

}
