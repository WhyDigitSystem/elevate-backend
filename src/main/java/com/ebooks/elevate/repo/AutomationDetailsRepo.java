package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.AutomationDetailsVO;
import com.ebooks.elevate.entity.AutomationVO;

public interface AutomationDetailsRepo extends JpaRepository<AutomationDetailsVO, Long> {

	List<AutomationDetailsVO> findByAutomationVO(AutomationVO automationVO);

}
