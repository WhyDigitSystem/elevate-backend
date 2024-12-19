package com.ebooks.elevate.repo;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.LedgerMappingVO;

@Repository
public interface LedgerMappingRepo extends JpaRepository<LedgerMappingVO, Long>{

	@Query(nativeQuery =true,value ="select c.accountgroupname,c.accountcode from coa c where c.type='Account' and c.active=1 and c.orgid=?1")
	Set<Object[]> getCOA(Long orgId);


	@Modifying
	@Transactional
	@Query("DELETE FROM LedgerMappingVO l WHERE l.clientCode = :clientCode")
	void deleteByClientCode(@Param("clientCode") String clientCode);

	@Query(nativeQuery =true,value ="select a.accountname, a.accountcode from ccoa a where a.accountcode not in(\r\n"
			+ "select clientcoacode from ledgermapping where clientcode=?1 and orgid=?2 group by \r\n"
			+ "clientcoacode) and a.clientcode=?1 and a.active=1 and a.orgid=?2 group by a.accountname, a.accountcode")
	Set<Object[]> getFillGridForLedgerMapping(String clientCode,Long orgId);


	boolean existsByOrgIdAndClientCoaCodeAndClientCode(Long orgId,String clientCoaCode, String clientCode);


	boolean existsByOrgIdAndClientCoaAndClientCode(Long orgId,String clientCoa, String clientCode);

}
