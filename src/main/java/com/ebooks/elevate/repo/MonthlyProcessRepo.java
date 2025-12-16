package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.MonthlyProcessVO;

public interface MonthlyProcessRepo extends JpaRepository<MonthlyProcessVO, Long> {

	

	List<MonthlyProcessVO> findByOrgIdAndClientCodeAndMainGroupAndSubGroupCodeAndYear(Long orgId, String clientCode,
			String mainGroup, String subGroupCode, String finYear);

	

	@Query(value = "select a from MonthlyProcessVO a where a.orgId=?1 and a.clientCode=?2 and a.year=?3 and a.month=?4 and a.mainGroup=?5 and a.subGroupCode=?6")
	MonthlyProcessVO getOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroupCode(Long orgId, String clientCode,
			String year, String month, String mainGroup, String subGroupCode);

	Optional<MonthlyProcessVO> findByOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroup(Long orgId,
			String clientCode, String year, String month, String mainGroup, String subGroup);


	@Query(value = "select a from MonthlyProcessVO a where a.orgId=?1 and a.clientCode=?2 and a.year=?3 and a.month=?4 and a.mainGroup=?5 and a.subGroup=?6")
	MonthlyProcessVO getOrgIdAndClientCodeAndYearAndMonthAndMainGroupAndSubGroup(Long orgId, String clientCode,
			String year, String month, String mainGroup, String subGroup);



	@Query(nativeQuery = true, value = "select distinct month,monthsequence from previousyearactual where clientcode=?2 and year=?1 and not (maingroup='Balance Sheet Schedule' and subgroup='Stock') \r\n"
			+ "and monthsequence > (select monthsequence from previousyearactual where clientcode=?2 and year=?1 and month=?3 group by monthsequence) and maingroup in(\r\n"
			+ "select maingroup from monthlyprocess where clientcode=?2 and year=?1 and month=?3 group by maingroup) order by monthsequence desc")
	List<Map<String, Object>> findProcessedMonths(String year, String clientCode, String month);



	@Query(nativeQuery = true, value = "select maingroup from monthlyprocess where clientcode=?2 and year=?1 and month=?3 group by maingroup")
	List<String> getProcessedMainGroup(String year, String clientCode, String month);



	@Query(nativeQuery = true, value = "select * from monthlyprocess where year=?1 and clientcode=?2 and month=?3 and maingroup=?4")
	List<MonthlyProcessVO> getDetails(String year, String clientCode, String month, String group);


}
