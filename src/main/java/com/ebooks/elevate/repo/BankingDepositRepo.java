package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.BankingDepositVO;

@Repository
public interface BankingDepositRepo extends JpaRepository<BankingDepositVO, Long>{

	@Query(nativeQuery = true, value = "select * from bankingdeposit where orgid=?1")
	List<BankingDepositVO> getAllBankingDepositByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from bankingdeposit where bankingdepositid=?1")
	List<BankingDepositVO> getBankingDepositById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBankingDepositDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBankingDepositByDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select accountgroupname from groupledger where orgid=?1 and category='bank'  and  active=1")
	Set<Object[]> findBankNameFromGroupforBankingDeposit(Long orgId);

}
