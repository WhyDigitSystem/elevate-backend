package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ElMfrVO;

@Repository
public interface ElMfrRepo extends JpaRepository<ElMfrVO, Long> {

	boolean existsByDescriptionAndOrgId(String description, Long orgId);

	boolean existsByElCodeAndOrgId(String elCode, Long orgId);

	@Query(nativeQuery = true, value = "select * FROM elmfr where orgid=?1")
	List<ElMfrVO> getAllElMfr(Long orgId);

	
	@Query(nativeQuery =true,value ="select row_number() over() sno,th.accountcode,th.accountname,th.action,th.screen from(\r\n"
			+ "SELECT \r\n"
			+ "    tb.accountcode,\r\n"
			+ "    tb.accountname,\r\n"
			+ "    'Pending Client COA Creation' AS action,\r\n"
			+ "    1 AS screen\r\n"
			+ "FROM tbexcelupload tb\r\n"
			+ "WHERE tb.clientcode = ?2\r\n"
			+ "  AND tb.orgid = ?1\r\n"
			+ "  AND tb.accountcode NOT IN (\r\n"
			+ "    SELECT accountcode \r\n"
			+ "    FROM ccoa \r\n"
			+ "    WHERE clientcode = ?2\r\n"
			+ "      AND orgid = ?1\r\n"
			+ "    GROUP BY accountcode\r\n"
			+ "  )\r\n"
			+ "  AND tb.accountcode NOT IN (\r\n"
			+ "    SELECT l.clientcoacode\r\n"
			+ "    FROM ledgermapping l\r\n"
			+ "    WHERE l.clientcode = ?2\r\n"
			+ "      AND l.orgid = ?1\r\n"
			+ "    GROUP BY l.clientcoacode\r\n"
			+ "  )\r\n"
			+ "GROUP BY tb.accountcode, tb.accountname, action, screen\r\n"
			+ "UNION\r\n"
			+ "SELECT \r\n"
			+ "    tb.accountcode,\r\n"
			+ "    tb.accountname,\r\n"
			+ "    'Pending Ledger Mapping' AS action,\r\n"
			+ "    2 AS screen\r\n"
			+ "FROM tbexcelupload tb\r\n"
			+ "WHERE tb.clientcode = ?2\r\n"
			+ "  AND tb.orgid = ?1\r\n"
			+ "  AND tb.accountcode IN (\r\n"
			+ "    SELECT accountcode \r\n"
			+ "    FROM ccoa \r\n"
			+ "    WHERE clientcode = ?2\r\n"
			+ "      AND orgid = ?1\r\n"
			+ "    GROUP BY accountcode\r\n"
			+ "  )\r\n"
			+ "  AND tb.accountcode NOT IN (\r\n"
			+ "    SELECT l.clientcoacode\r\n"
			+ "    FROM ledgermapping l\r\n"
			+ "    WHERE l.clientcode = ?2\r\n"
			+ "      AND l.orgid = ?1\r\n"
			+ "    GROUP BY l.clientcoacode\r\n"
			+ "  )\r\n"
			+ "GROUP BY tb.accountcode, tb.accountname, action, screen)th")
	Set<Object[]> getMisMatchClientTb(Long orgId, String clientCode);

}
