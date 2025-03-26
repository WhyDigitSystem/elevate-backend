package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.GroupMappingVO;

public interface GroupMappingRepo extends JpaRepository<GroupMappingVO, Long> {

	boolean existsByOrgIdAndGroupNameIgnoreCase(Long orgId, String groupName);

	@Query(nativeQuery =true,value ="select * from groupmapping where orgid=?1")
	List<GroupMappingVO> getGroupMappingAll(Long orgId);

	@Query(nativeQuery = true,value = "select c.accountgroupname,c.accountcode from groupmapping a, subgroup b,coa c where a.groupmappingid=b.groupmappingid and a.orgid=?1 and a.groupname=?2 \r\n"
			+ " and b.active=1 and a.orgid=c.orgid and b.accountcode=c.accountcode and c.active=1 group by\r\n"
			+ " c.accountcode,c.accountgroupname")
	Set<Object[]> getSubGroupDetails(Long orgId, String mainGroup);
	
	
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
			+ "    SELECT c.accountgroupname AS accountname, c.accountcode, c.natureofaccount \r\n"
			+ "    FROM groupmapping a\r\n"
			+ "    JOIN subgroup b ON a.groupmappingid = b.groupmappingid\r\n"
			+ "    JOIN groupledgers d ON b.accountcode = d.subgroupcode AND d.active = 1\r\n"
			+ "    JOIN coa c ON b.accountcode = c.parentcode AND c.orgid = d.orgid AND c.accountcode = d.accountcode AND c.active = 1\r\n"
			+ "    WHERE a.orgid = ?1  \r\n"
			+ "    AND a.groupname = ?4  \r\n"
			+ "    AND b.accountcode = ?5  \r\n"
			+ "    AND b.active = 1\r\n"
			+ "    GROUP BY c.accountgroupname, c.accountcode, c.natureofaccount\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    a.accountname, \r\n"
			+ "    a.accountcode, \r\n"
			+ "    a.natureofaccount, \r\n"
			+ "    m.month, \r\n"
			+ "    COALESCE(SUM(b.amount), 0) AS totalamount\r\n"
			+ "FROM Accounts a\r\n"
			+ "CROSS JOIN Months m\r\n"
			+ "LEFT JOIN budget b \r\n"
			+ "    ON a.accountcode = b.accountcode \r\n"
			+ "    AND b.orgid = ?1 \r\n"
			+ "    AND b.year = ?2 \r\n"
			+ "    AND b.clientcode = ?3\r\n"
			+ "    AND b.maingroup = ?4 \r\n"
			+ "    AND b.subgroupcode = ?5\r\n"
			+ "    AND b.month = m.month\r\n"
			+ "GROUP BY a.accountname, a.accountcode, a.natureofaccount, m.month\r\n"
			+ "ORDER BY a.accountcode, \r\n"
			+ "         FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')")
	Set<Object[]> getGroupLedgersDetails(Long orgId,String year,String clientCode, String mainGroup,String accountCode);

	@Query(nativeQuery = true, value = "WITH Months AS (\r\n"
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
			+ "    SELECT c.accountgroupname AS accountname, c.accountcode, c.natureofaccount \r\n"
			+ "    FROM groupmapping a\r\n"
			+ "    JOIN subgroup b ON a.groupmappingid = b.groupmappingid\r\n"
			+ "    JOIN groupledgers d ON b.accountcode = d.subgroupcode AND d.active = 1\r\n"
			+ "    JOIN coa c ON b.accountcode = c.parentcode AND c.orgid = d.orgid AND c.accountcode = d.accountcode AND c.active = 1\r\n"
			+ "    WHERE a.orgid = ?1  \r\n"
			+ "    AND a.groupname = ?4  \r\n"
			+ "    AND b.accountcode = ?5  \r\n"
			+ "    AND b.active = 1\r\n"
			+ "    GROUP BY c.accountgroupname, c.accountcode, c.natureofaccount\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    a.accountname, \r\n"
			+ "    a.accountcode, \r\n"
			+ "    a.natureofaccount, \r\n"
			+ "    m.month, \r\n"
			+ "    COALESCE(SUM(b.amount), 0) AS totalamount\r\n"
			+ "FROM Accounts a\r\n"
			+ "CROSS JOIN Months m\r\n"
			+ "LEFT JOIN previousyearactual b \r\n"
			+ "    ON a.accountcode = b.accountcode \r\n"
			+ "    AND b.orgid = ?1 \r\n"
			+ "    AND b.year = ?2 \r\n"
			+ "    AND b.clientcode = ?3\r\n"
			+ "    AND b.maingroup = ?4 \r\n"
			+ "    AND b.subgroupcode = ?5\r\n"
			+ "    AND b.month = m.month\r\n"
			+ "GROUP BY a.accountname, a.accountcode, a.natureofaccount, m.month\r\n"
			+ "ORDER BY a.accountcode, \r\n"
			+ "         FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')")
	Set<Object[]> PreviousYearGroupLedgersDetails(Long orgId, String year, String clientCode, String mainGroup,
			String subGroupCode);
}
