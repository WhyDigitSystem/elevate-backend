package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.MonthlyProcessDetailsVO;
import com.ebooks.elevate.entity.MonthlyProcessVO;

public interface MonthlyProcessDetailsRepo extends JpaRepository<MonthlyProcessDetailsVO, Long>{

	List<MonthlyProcessDetailsVO> findByMonthlyProcessVO(MonthlyProcessVO monthlyProcessVO);
	
	
	

}
