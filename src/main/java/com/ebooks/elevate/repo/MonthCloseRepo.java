package com.ebooks.elevate.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.MonthCloseVO;

public interface MonthCloseRepo extends JpaRepository<MonthCloseVO, Long> {

	Optional<MonthCloseVO> findByClientCodeAndFinYearAndMonth(String clientCode, String year, String month);

	@Query(nativeQuery = true, value = "select month from previousyearactual where clientcode=?1 and year=?2 and monthsequence=(\r\n"
			+ "SELECT \r\n"
			+ "  CASE \r\n"
			+ "    WHEN COALESCE(MAX(monthsequnce), 0) + 1 > 12 THEN 0 \r\n"
			+ "    ELSE COALESCE(MAX(monthsequnce), 0) + 1 \r\n"
			+ "  END AS next_month_sequence\r\n"
			+ "FROM monthclose \r\n"
			+ "WHERE clientcode = ?1 \r\n"
			+ "  AND finyear = ?2 \r\n"
			+ "  AND status = 'CLOSED') group by month")
	String getUnclosedMonth(String clientCode, String year);


}
