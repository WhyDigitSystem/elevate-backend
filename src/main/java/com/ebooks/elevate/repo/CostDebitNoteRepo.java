package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteRepo extends JpaRepository<CostDebitNoteVO, Long>{
	
    @Query(value ="SELECT * FROM costdebitnote where orgid=?1",nativeQuery =true)
	List<CostDebitNoteVO> getByCostDebitNoteByOrgId(Long orgId);
    
    @Query(value ="SELECT * FROM costdebitnote where costdebitnoteid=?1",nativeQuery =true)
	List<CostDebitNoteVO> getByCostDebitNoteById(Long id);
    
    @Query(value ="SELECT * FROM costdebitnote where active=1",nativeQuery =true)
	List<CostDebitNoteVO> getActiveCostDebitNote();

    @Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getCostDebitNoteDocId(Long orgId, String finYear, String branchCode, String screenCode);

    @Query(nativeQuery = true, value = "select * from CostDebitNoteVO where orgId=?1 and docId=?2")
	CostDebitNoteVO findAllCostDebitNoteByDocId(Long orgId, String docId);

	 @Query(value="SELECT c.chargeType, c.chargeCode, c.govtSac, c.serviceAccountCode FROM ChargeTypeRequest c WHERE c.orgId =?1 AND c.purchaseAccount IS NOT NULL",nativeQuery =true)
	Set<Object[]> getChareDetails(Long orgId);

	@Query(nativeQuery =true,value ="SELECT a.partyname, a.partycode, a.partytype, b.addresstype\r\n"
			+ "FROM partymaster a\r\n"
			+ "JOIN partyaddress b ON a.partymasterid = b.partymasterid  \r\n"
			+ "WHERE a.orgId = ?1\r\n"
			+ "AND a.branch = ?2\r\n"
			+ "AND a.finyear =?3")
	Set<Object[]> getParty(Long orgId,String branch,String finYear);

	@Query(value ="select docid from costinvoice where orgid=?1",nativeQuery =true)
	Set<Object[]> getDocIdForCI(Long orgId);

	@Query(nativeQuery = true,value="select currency,currencydescripition,buyingexrate,sellingexrate from vw_exrates where orgid=?1")
	Set<Object[]> getCurrencyAndExrateDetails(Long orgId);

}
