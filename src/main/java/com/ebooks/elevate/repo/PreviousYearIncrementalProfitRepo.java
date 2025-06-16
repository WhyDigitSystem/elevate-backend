package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.PreviousYearIncrementalProfitVO;

public interface PreviousYearIncrementalProfitRepo extends JpaRepository<PreviousYearIncrementalProfitVO, Long> {

	@Query(nativeQuery = true,value = "select * from pyincrementalprofit where orgid=?1 and clientcode=?2 and year=?3 ")
	List<PreviousYearIncrementalProfitVO> getClientBudgetDls(Long org, String clientcode, String yr);

}
