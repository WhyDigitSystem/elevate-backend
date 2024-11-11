package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ArBillBalanceVO;

public interface ArBillBalanceRepo extends JpaRepository<ArBillBalanceVO, Long> {

	@Query(nativeQuery = true, value = "select * from arbillbalance where orgid=?1")
	List<ArBillBalanceVO> getAllArBillBalanceByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from arbillbalance where arbillbalanceid=?1")
	List<ArBillBalanceVO> getAllArBillBalanceById(Long id);

	@Query(nativeQuery = true, value = "select * from arbillbalance where active=1")
	List<ArBillBalanceVO> findArBillBalanceByActive();

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getArBillBalanceDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select partyname,partycode from partymaster where orgid=?1 and active=1 and  partytype='CUSTOMER'")
	Set<Object[]> getPartyNameAndCodeForArBillBalance(Long orgId);
}
