package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.DocumentTypeMappingVO;

@Repository
public interface DocumentTypeMappingRepo extends JpaRepository<DocumentTypeMappingVO, Long> {

	@Query(nativeQuery = true,value = " SELECT \r\n"
			+ "    b.screenname, \r\n"
			+ "    b.screencode, \r\n"
			+ "    b.doccode,\r\n"
			+ "    ?4 AS finyear,\r\n"
			+ "    ?2 AS branch,\r\n"
			+ "    ?3 AS branchcode,\r\n"
			+ "    ?5 AS finyearidentifier,\r\n"
			+ "    CONCAT(?3, ?4,b.clientcode, b.doccode) AS prefixfield,b.client,b.clientcode \r\n"
			+ "FROM (\r\n"
			+ "SELECT j.doccode, j.screencode, j.screenname ,k.clientcode,k.name client\r\n"
			+ "    FROM documenttype j,clientcompany k \r\n"
			+ "     where j.orgid=k.orgid and concat(?4,?2,k.clientcode,j.screencode) NOT IN (\r\n"
			+ "        SELECT concat(jk.finyear,jk.branch,jk.clientcode,jk.screencode) \r\n"
			+ "        FROM documenttypemappingdetails jk\r\n"
			+ "        WHERE jk.finyear = ?4 AND jk.orgid = ?1\r\n"
			+ "          AND jk.branch =?2\r\n"
			+ "    ) AND j.orgid = ?1)b")
	Set<Object[]> getPendingDoctypeMapping(Long orgId, String branch, String branchCode, String finYear,
			String finYearIdentifier);

	@Query(value = "select a from DocumentTypeMappingVO a where a.orgId=?1")
	List<DocumentTypeMappingVO> findByOrgId(Long orgId);

	

}
