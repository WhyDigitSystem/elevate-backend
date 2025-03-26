package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.MonthlyProcessVO;

public interface MonthlyProcessRepo extends JpaRepository<MonthlyProcessVO, Long> {

	

	List<MonthlyProcessVO> findByOrgIdAndClientCodeAndMainGroupAndSubGroupCodeAndYear(Long orgId, String clientCode,
			String mainGroup, String subGroupCode, String finYear);

	Optional<MonthlyProcessVO> findByOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroupCode(Long orgId,
			String clientCode, String year, String month, String mainGroup, String subGroupCode);

	@Query(value = "select a from MonthlyProcessVO a where a.orgId=?1 and a.clientCode=?2 and a.year=?3 and a.month=?4 and a.mainGroup=?5 and a.subGroupCode=?6")
	MonthlyProcessVO getOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroupCode(Long orgId, String clientCode,
			String year, String month, String mainGroup, String subGroupCode);


}
