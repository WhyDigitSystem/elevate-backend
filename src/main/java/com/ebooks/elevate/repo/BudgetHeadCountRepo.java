package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.BudgetHeadCountVO;

public interface BudgetHeadCountRepo extends JpaRepository<BudgetHeadCountVO, Long> {

	@Query(nativeQuery = true,value = "select a.* from budgetheadcount a where a.orgid=?1 and a.clientcode=?2 and a.year=?3")
	List<BudgetHeadCountVO> getClientBudgetDls(Long org, String clientcode, String yr);

	@Query(nativeQuery = true,value = "WITH Months AS (\r\n"
			+ "    SELECT 'April' AS month UNION ALL\r\n"
			+ "    SELECT 'May' UNION ALL\r\n"
			+ "    SELECT 'June' UNION ALL\r\n"
			+ "    SELECT 'July' UNION ALL\r\n"
			+ "    SELECT 'August' UNION ALL\r\n"
			+ "    SELECT 'September' UNION ALL\r\n"
			+ "    SELECT 'October' UNION ALL\r\n"
			+ "    SELECT 'November' UNION ALL\r\n"
			+ "    SELECT 'December' UNION ALL\r\n"
			+ "    SELECT 'January' UNION ALL\r\n"
			+ "    SELECT 'February' UNION ALL\r\n"
			+ "    SELECT 'March'\r\n"
			+ "),\r\n"
			+ "Accounts AS (\r\n"
			+ "    select department,employmenttype,category,month,sum(headcount)headcount,sum(ctc)ctc from budgetheadcount where orgid=?1 and clientcode=?3 and year=?2 group by \r\n"
			+ "department,employmenttype,category,month\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    a.department,a.employmenttype,a.category,\r\n"
			+ "    m.month, \r\n"
			+ "    b.headcount,b.ctc\r\n"
			+ "FROM Accounts a\r\n"
			+ "CROSS JOIN Months m \r\n"
			+ "left join budgetheadcount b\r\n"
			+ "    on b.orgid = ?1\r\n"
			+ "    AND b.year = ?2\r\n"
			+ "    AND b.clientcode = ?3\r\n"
			+ "    AND b.month = m.month and a.department=b.department\r\n"
			+ "    and a.employmenttype=b.employmenttype\r\n"
			+ "    and a.category=b.category \r\n"
			+ "GROUP BY \r\n"
			+ "    a.department,a.employmenttype,a.category,\r\n"
			+ "    m.month, \r\n"
			+ "    b.headcount,b.ctc,\r\n"
			+ "    FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')\r\n"
			+ "ORDER BY \r\n"
			+ "    FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')")
	Set<Object[]> getDetails(Long orgId, String year, String clientCode);

}
