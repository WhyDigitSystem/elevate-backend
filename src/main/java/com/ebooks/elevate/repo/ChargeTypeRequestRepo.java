package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ChargeTypeRequestVO;

public interface ChargeTypeRequestRepo extends JpaRepository<ChargeTypeRequestVO, Long> {

	@Query(nativeQuery = true, value = "select * from chargetyperequest where chargetyperequestid=?1")
	List<ChargeTypeRequestVO> getAllChargeTypeRequestById(Long id);

	@Query(nativeQuery = true, value = "select * from chargetyperequest where orgid=?1")
	List<ChargeTypeRequestVO> getAllChargeTypeRequestByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from chargetyperequest where active=1")
	List<ChargeTypeRequestVO> findChargeTypeRequestByActive();


	@Query(nativeQuery = true, value = "select accountgroupname from groupledger where active=1 and category in('RECEIVABLE A/C','OTHERS') and type='ACCOUNT' and orgid=?1")
	Set<Object[]> findSalesAccountFromGroup(Long orgId);

	@Query(nativeQuery = true, value = "select accountgroupname from groupledger where active=1 and category in('PAYABLE A/C','OTHERS') and type='ACCOUNT' and orgid=?1")
	Set<Object[]> findPaymentAccountFromGroup(Long orgId);

	boolean existsByOrgIdAndChargeDescriptionIgnoreCase(Long orgId, String chargeDescription);

	boolean existsByOrgIdAndChargeCodeIgnoreCase(Long orgId, String chargeCode);

	@Query(value = "select a.chargeType from ChargeTypeRequestVO a where a.orgId=?1 and a.active=true group by a.chargeType")
	Set<Object[]> getActiveChargType(Long orgId);
	@Query(nativeQuery = true, value = "select chargecode,govtsac,chargedescription,taxable,ccfeeapplicable,excempted,serviceaccountcode,gsttax,salesaccount from chargetyperequest\r\n"
			+ " where orgid=?1 and chargetype=?2 and active=1 group by chargecode,govtsac,chargedescription,taxable,ccfeeapplicable,excempted,serviceaccountcode,gsttax,salesaccount")
	Set<Object[]> getActiveChargCodeByOrgIdAndChargeTypeIgnoreCase(Long orgId, String chargeType);
}
