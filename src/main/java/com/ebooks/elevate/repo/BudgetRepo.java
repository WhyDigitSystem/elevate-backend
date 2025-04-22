package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.BudgetVO;

public interface BudgetRepo extends JpaRepository<BudgetVO, Long> {

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    ROW_NUMBER() OVER (ORDER BY accountcode) AS `ELGLS.No`,\r\n"
			+ "    accountname AS `ELGL`,\r\n"
			+ "    accountcode AS `ELGLCode`,\r\n"
			+ "    natureofaccount AS `NLDN/CR`,\r\n"
			+ "    MAX(CASE WHEN month = 'January' THEN amount ELSE 0 END) AS 'January',\r\n"
			+ "    MAX(CASE WHEN month = 'February' THEN amount ELSE 0 END) AS 'February',\r\n"
			+ "    MAX(CASE WHEN month = 'March' THEN amount ELSE 0 END) AS 'March',\r\n"
			+ "    MAX(CASE WHEN month = 'April' THEN amount ELSE 0 END) AS 'April',\r\n"
			+ "    MAX(CASE WHEN month = 'May' THEN amount ELSE 0 END) AS 'May',\r\n"
			+ "    MAX(CASE WHEN month = 'June' THEN amount ELSE 0 END) AS 'June',\r\n"
			+ "    MAX(CASE WHEN month = 'July' THEN amount ELSE 0 END) AS 'July',\r\n"
			+ "    MAX(CASE WHEN month = 'August' THEN amount ELSE 0 END) AS 'August',\r\n"
			+ "    MAX(CASE WHEN month = 'September' THEN amount ELSE 0 END) AS 'September',\r\n"
			+ "    MAX(CASE WHEN month = 'October' THEN amount ELSE 0 END) AS 'October',\r\n"
			+ "    MAX(CASE WHEN month = 'November' THEN amount ELSE 0 END) AS 'November',\r\n"
			+ "    MAX(CASE WHEN month = 'December' THEN amount ELSE 0 END) AS 'December'\r\n"
			+ "FROM budget where year=?2 and orgid=?1 and client=?3 and clientcode=?4 \r\n"
			+ "GROUP BY accountname, accountcode, natureofaccount\r\n"
			+ "ORDER BY accountcode")
	Set<Object[]> getClientBudgetDetails(Long orgId, String year, String client, String clientCode);

	@Query(nativeQuery = true,value = "select * from budget where orgid=?1 and clientcode=?2 and year=?3 and month=?4 and maingroup=?5 and subgroupcode=?6 and accountcode=?7")
	BudgetVO getBudgetDetails(Long orgId, String clientCode, String year, String month, String mainGroup,
			String subGroupCode, String accountCode);

	@Query(nativeQuery = true,value = "select g.maingroup,g.subgroup,g.subgroupcode,g.accountcode,g.accountname,g.natureofaccount,g.quater,sum(g.currentYear)currentYear,sum(g.previousYear)previousYear from(\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,quater,sum(amount) currentYear,0 previousYear from budget where orgid=?1 and clientcode=?4 and year=?2\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,natureofaccount,accountname,quater \r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,quater,0 currentYear,sum(amount)previousYear from budget where orgid=?1 and clientcode=?4 and year=?3\r\n"
			+ "group by maingroup,subgroup,natureofaccount,subgroupcode,accountcode,accountname,quater) g where g.maingroup=?5 and g.subgroup=?6 group by g.maingroup,g.subgroup,g.natureofaccount,g.subgroupcode,g.accountcode,g.accountname,g.quater \r\n"
			+ "order by g.quater asc")
	Set<Object[]> getELBudgetDetails(Long orgId, String finyear, String previousYear, String clientCode,
			String mainGroupName, String subGroupCode);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    a.maingroup,\r\n"
			+ "    a.subgroup,\r\n"
			+ "    a.subgroupcode,\r\n"
			+ "    a.accountcode,\r\n"
			+ "    a.accountname,\r\n"
			+ "    a.natureofaccount,\r\n"
			+ "    ROUND(SUM(a.budget), 2) AS budget,\r\n"
			+ "    ROUND(SUM(a.actual), 2) AS actual,\r\n"
			+ "    ROUND(SUM(a.PY), 2) AS PY,\r\n"
			+ "    ROUND(SUM(a.budgetYTD), 2) AS budgetYTD,\r\n"
			+ "    ROUND(SUM(a.ActYTD), 2) AS ActYTD,\r\n"
			+ "    ROUND(SUM(a.PyYTD), 2) AS PyYTD,\r\n"
			+ "    ROUND(SUM(a.fullBudget), 2) AS fullBudget,\r\n"
			+ "    ROUND(\r\n"
			+ "        (SUM(a.ActYTD) / \r\n"
			+ "        (SELECT monthsequence FROM budget WHERE year=?3 AND month=?5 GROUP BY monthsequence) * 12),\r\n"
			+ "        2\r\n"
			+ "    ) AS Estimate,\r\n"
			+ "    ROUND(SUM(a.fullPY), 2) AS fullPY\r\n"
			+ "FROM (\r\n"
			+ "    select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,sum(amount) budget,0 actual,0 PY,0 budgetYTD,0 ActYTD,0 PyYTD,0 fullBudget,0 fullPY from budget where orgid=?1 and clientcode=?2 and year=?3 and month=?5\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,natureofaccount,accountname \r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,sum(amount)actual,0 PY,0 budgetYTD,0 ActYTD,0 PyYTD,0 fullBudget,0 fullPY from previousyearactual where orgid=?1 and clientcode=?2 and year=?3 and month=?5  group by maingroup,subgroup,natureofaccount,subgroupcode,accountcode,accountname\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,sum(amount)PY,0 budgetYTD,0 ActYTD,0 PyYTD,0 fullBudget,0 fullPY from previousyearactual where orgid=?1 and clientcode=?2 and year=?4 and month=?5  group by maingroup,subgroup,natureofaccount,subgroupcode,accountcode,accountname\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,0 PY,sum(amount)budgetYTD,0 ActYTD,0 PyYTD,0 fullBudget,0 fullPY from budget where orgid=?1 and clientcode=?2 and year=?3  and monthsequence<=(select monthsequence from budget where year=?3 and month=?5 group by monthsequence)\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,natureofaccount,accountname,quater\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,0 PY,0 budgetYTD,sum(amount) ActYTD,0 PyYTD,0 fullBudget,0 fullPY from previousyearactual where orgid=?1 and clientcode=?2 and year=?3 and monthsequence<=(select monthsequence from previousyearactual where year=?3 and month=?5 group by monthsequence)\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,0 PY,0 budgetYTD,0 ActYTD,sum(amount) PyYTD,0 fullBudget,0 fullPY from previousyearactual where orgid=?1 and clientcode=?2 and year=?4 and monthsequence<=(select monthsequence from previousyearactual where year=?4 and month=?5 group by monthsequence)\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,0 PY,0 budgetYTD,0 ActYTD,0 PyYTD,sum(amount)fullBudget,0 fullPY from budget a where a.orgid=?1 and a.clientcode=?2 and a.year=?3\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount\r\n"
			+ "union\r\n"
			+ "select maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount,0 budget,0 actual,0 PY,0 budgetYTD,0 ActYTD,0 PyYTD,0 fullBudget,sum(amount) fullPY from previousyearactual a where a.orgid=?1 and a.clientcode=?2 and a.year=?4\r\n"
			+ "group by maingroup,subgroup,subgroupcode,accountcode,accountname,natureofaccount\r\n"
			+ ") a where a.maingroup=?6 and a.subgroup=?7\r\n"
			+ "GROUP BY \r\n"
			+ "    a.maingroup,\r\n"
			+ "    a.subgroup,\r\n"
			+ "    a.subgroupcode,\r\n"
			+ "    a.accountcode,\r\n"
			+ "    a.accountname,\r\n"
			+ "    a.natureofaccount")
	Set<Object[]> getELActualDetails(Long orgId, String clientCode, String finyear, String previousYear, String month,
			String mainGroupName, String subGroupCode);


	

}
