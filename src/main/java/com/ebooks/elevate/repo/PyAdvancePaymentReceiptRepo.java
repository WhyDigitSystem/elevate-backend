package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.PyAdvancePaymentReceiptVO;

public interface PyAdvancePaymentReceiptRepo extends JpaRepository<PyAdvancePaymentReceiptVO, Long> {

	
	@Query(nativeQuery = true,value = "select a.* from pyadvancepr a where a.orgid=?1 and a.clientcode=?2 and a.year=?3 and a.type=?4")
	List<PyAdvancePaymentReceiptVO> getClientBudgetDls(Long org, String clientcode, String yr, String type);

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
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    b.party,\r\n"
			+ "    m.month,\r\n"
			+ "    COALESCE(SUM(b.amount), 0) AS totalamount\r\n"
			+ "FROM Months m\r\n"
			+ "LEFT JOIN pyadvancepr b \r\n"
			+ "    ON b.orgid = ?1\r\n"
			+ "    AND b.year = ?2\r\n"
			+ "    AND b.clientcode = ?3\r\n"
			+ "    and b.type=?4\r\n"
			+ "    AND b.month = m.month\r\n"
			+ "GROUP BY \r\n"
			+ "    b.party,\r\n"
			+ "    m.month,\r\n"
			+ "    FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')\r\n"
			+ "ORDER BY\r\n"
			+ "    FIELD(m.month, 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 'January', 'February', 'March')")
	Set<Object[]> getPaymentReceiptDetails(Long orgId, String year, String clientCode, String type);

}
