package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.PreviousYearActualVO;

public interface PreviousYearActualRepo extends JpaRepository<PreviousYearActualVO, Long>{

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
			+ "FROM previousyearactual where year=?2 and orgid=?1 and client=?3 and clientcode=?4 \r\n"
			+ "GROUP BY accountname, accountcode, natureofaccount\r\n"
			+ "ORDER BY accountcode")
	Set<Object[]> getClientPreviousYearActualDetails(Long orgId, String year, String client, String clientCode);

	@Query(nativeQuery = true,value = "select * from previousyearactual where orgid=?1 and clientcode=?2 and year=?3 and month=?4 and maingroup=?5 and subgroupcode=?6 and accountcode=?7")
	PreviousYearActualVO getPreviousYearDetails(Long orgId, String clientCode, String year, String month,
			String mainGroup, String subGroupCode, String accountCode);

	@Query(nativeQuery = true,value = "SELECT a.maingroup, a.subgroup, a.subgroupcode, a.accountcode, a.accountname, \r\n"
			+ "       a.natureofaccount, a.quater, SUM(a.previousYear) AS previousYear \r\n"
			+ "FROM (\r\n"
			+ "    SELECT maingroup, subgroup, subgroupcode, accountcode, accountname, natureofaccount, quater, \r\n"
			+ "           SUM(amount) AS previousYear \r\n"
			+ "    FROM previousyearactual \r\n"
			+ "    WHERE orgid = ?1 \r\n"
			+ "      AND clientcode = ?3 \r\n"
			+ "      AND year = ?2 \r\n"
			+ "      AND monthsequence <= (\r\n"
			+ "          SELECT MAX(monthsequence) \r\n"
			+ "          FROM previousyearactual \r\n"
			+ "          WHERE year = ?2 \r\n"
			+ "            AND month = ?6 \r\n"
			+ "            AND clientcode = ?3\r\n"
			+ "      )\r\n"
			+ "    GROUP BY maingroup, subgroup, natureofaccount, subgroupcode, accountcode, accountname, quater \r\n"
			+ "\r\n"
			+ "    UNION \r\n"
			+ "\r\n"
			+ "    SELECT maingroup, subgroup, subgroupcode, accountcode, accountname, natureofaccount, \r\n"
			+ "           'YTD' AS quater, SUM(amount) AS previousYear \r\n"
			+ "    FROM previousyearactual \r\n"
			+ "    WHERE orgid = ?1 \r\n"
			+ "      AND clientcode = ?3 \r\n"
			+ "      AND year = ?2 \r\n"
			+ "      AND monthsequence <= (\r\n"
			+ "          SELECT MAX(monthsequence) \r\n"
			+ "          FROM previousyearactual \r\n"
			+ "          WHERE year = ?2 \r\n"
			+ "            AND month = ?6 \r\n"
			+ "            AND clientcode = ?3\r\n"
			+ "      )\r\n"
			+ "    GROUP BY maingroup, subgroup, natureofaccount, subgroupcode, accountcode, accountname\r\n"
			+ ") AS a  -- Alias for the derived table\r\n"
			+ "WHERE a.maingroup = ?4 AND a.subgroupcode = ?5  -- Fixed condition (AND instead of ,)\r\n"
			+ "GROUP BY a.maingroup, a.subgroup, a.subgroupcode, a.accountcode, a.accountname, a.natureofaccount, a.quater;\r\n"
			+ "")
	Set<Object[]> getELPYDetails(Long orgId, String finyear, String clientCode, String mainGroupName,
			String subGroupCode,String month);

	

}
