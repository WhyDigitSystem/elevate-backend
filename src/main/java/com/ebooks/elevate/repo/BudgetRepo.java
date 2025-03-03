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

}
