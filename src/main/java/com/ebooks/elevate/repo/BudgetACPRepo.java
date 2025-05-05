package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.BudgetACPVO;

public interface BudgetACPRepo extends JpaRepository<BudgetACPVO, Long> {

	@Query(nativeQuery = true,value = "select a.* from budgetaccountspayable a where a.orgid=?1 and a.clientcode=?2 and a.year=?3 and a.month=?4")
	List<BudgetACPVO> getClientBudgetDls(Long org, String clientcode, String yr, String month);
	
	@Query(nativeQuery = true, value = "SELECT supplier, paymentterms, grosspurchase, noofmonthpurchase, outstanding, paymentperiod, slab1, slab2, slab3, slab4, slab5\r\n"
			+ "FROM budgetaccountspayable\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND clientcode = ?4\r\n"
			+ "  AND month = ?3\r\n"
			+ "  AND year = ?2\r\n"
			+ "\r\n"
			+ "UNION ALL\r\n"
			+ "\r\n"
			+ "-- Fallback query: only runs if no data found above\r\n"
			+ "SELECT supplier, paymentterms, \r\n"
			+ "       NULL AS grosspurchase, NULL AS noofmonthpurchase, NULL AS outstanding, NULL AS paymentperiod, \r\n"
			+ "       NULL AS slab1, NULL AS slab2, NULL AS slab3, NULL AS slab4, NULL AS slab5\r\n"
			+ "FROM budgetaccountspayable\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND clientcode = ?4\r\n"
			+ "  AND NOT EXISTS (\r\n"
			+ "    SELECT 1 FROM budgetaccountspayable\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND clientcode = ?4\r\n"
			+ "      AND month = ?3\r\n"
			+ "      AND year = ?2\r\n"
			+ "  )\r\n"
			+ "GROUP BY supplier, paymentterms")
	Set<Object[]> getBudgetACPDetails(Long orgId,String year,String month, String clientCode);
	
	
	@Query(nativeQuery = true,value = "select b.unit from clientcompany a, clientunit b where a.clientcompanyid=b.clientcompanyid and b.active=1 and a.orgid=?1 and a.clientcode=?2")
	Set<Object[]> getUnitDetails(Long orgId, String clientCode);

}
