package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ebooks.elevate.entity.PreviousYearIncrementalProfitVO;

public interface PreviousYearIncrementalProfitRepo extends JpaRepository<PreviousYearIncrementalProfitVO, Long> {

	@Query(nativeQuery = true,value = "select * from pyincrementalprofit where orgid=?1 and clientcode=?2 and year=?3 and subgroup=?4 and month=?5")
	List<PreviousYearIncrementalProfitVO> getClientBudgetDls(Long org, String clientcode, String yr,String subGroup,String month);
	
	
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM pyincrementalprofit " +
                   "WHERE orgid = ?1 " +
                   "AND clientcode = ?2 " +
                   "AND year = ?3 " +
                   "AND subgroup = ?4 " +
                   "AND month = ?5", nativeQuery = true)
	void deleteByOrgIdAndClientAndYearAndSubGroupAndMonth(Long orgId, String clientCode, String year,String subGroup, String month);

}
