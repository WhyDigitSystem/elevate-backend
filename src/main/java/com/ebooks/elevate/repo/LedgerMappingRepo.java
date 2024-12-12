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

	@Query(nativeQuery =true,value ="select c.accountgroupname,c.accountcode from ccoa c where c.type='Account' and c.active=1")
	Set<Object[]> getFullGridForLedgerMapping();

	@Modifying
	@Transactional
	@Query("DELETE FROM LedgerMappingVO l WHERE l.clientCode = :clientCode")
	void deleteByClientCode(@Param("clientCode") String clientCode);
}
