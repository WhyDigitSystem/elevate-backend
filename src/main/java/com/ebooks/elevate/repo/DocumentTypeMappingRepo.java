package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.DocumentTypeMappingVO;

@Repository
public interface DocumentTypeMappingRepo extends JpaRepository<DocumentTypeMappingVO, Long> {

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    b.screenname, \r\n"
			+ "    b.screencode, \r\n"
			+ "    b.doccode,\r\n"
			+ "    ?4 finyear,\r\n"
			+ "    ?2 AS branch,\r\n"
			+ "    ?3 AS branchcode,\r\n"
			+ "    ?5 AS finyearidentifier,\r\n"
			+ "    CONCAT(?3, ?5,b.doccode) AS prefixfield\r\n"
			+ "FROM (\r\n"
			+ "    SELECT doccode, screencode, screenname \r\n"
			+ "    FROM documenttype  \r\n"
			+ "    WHERE CONCAT(screencode, doccode) NOT IN (\r\n"
			+ "        SELECT CONCAT(screencode, doccode) \r\n"
			+ "        FROM documenttypemappingdetails \r\n"
			+ "        WHERE finyear = ?4 and orgid = ?1\r\n"
			+ "          AND branch = ?2\r\n"
			+ "    ) and orgid = ?1\r\n"
			+ ") b")
	Set<Object[]> getPendingDoctypeMapping(Long orgId, String branch, String branchCode, String finYear,
			String finYearIdentifier);

	@Query(value = "select a from DocumentTypeMappingVO a where a.orgId=?1")
	List<DocumentTypeMappingVO> findByOrgId(Long orgId);

	

}
