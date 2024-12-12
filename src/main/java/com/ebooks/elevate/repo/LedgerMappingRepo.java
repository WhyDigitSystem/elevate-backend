package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.LedgerMappingVO;

@Repository
public interface LedgerMappingRepo extends JpaRepository<LedgerMappingVO, Long>{

	@Query(nativeQuery =true,value ="select c.accountgroupname,c.accountcode from ccoa c where c.type='Account' and c.active=1")
	Set<Object[]> getFullGridForLedgerMapping();

	@Query(nativeQuery =true,value ="SELECT ccoa.accountgroupname,ccoa.accountcode,lm.coa,lm.coacode FROM ccoa\r\n"
			+ "LEFT OUTER JOIN ledgermapping lm ON ccoa.accountcode = lm.clientcoacode and ccoa.clientid=lm.clientcode\r\n"
			+ "WHERE ccoa.clientid = ?1 AND ccoa.type = 'Account'")
	Set<Object[]> getFillGridForLedgerMapping(String clientCode);

}
