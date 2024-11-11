package com.ebooks.elevate.repo;

import java.util.List;	
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.TmsJobCardVO;

@Repository
public interface TmsJobCardRepo extends JpaRepository<TmsJobCardVO, Long> {

	@Query(nativeQuery = true, value = "select * from tmsjobcard where orgid=?1")
	List<TmsJobCardVO> getAllTmsJobCardByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from tmsjobcard where tmsjobcardid=?1")
	List<TmsJobCardVO> getAllTmsJobCardById(Long id);

	@Query(nativeQuery = true, value = "select  a1.salesperson from partymaster a,partysalespersontagging a1 where\r\n"
			+ "active=1 and  a.partymasterid=a1.partymasterid \r\n"
			+ " and a.partyname=?1 and  a.orgid=?2\r\n"
			+ "group by a1.salesperson")
	Set<Object[]> findBySalesPreson(Long orgId,String partyName);
	
	@Query(nativeQuery = true,value="select partyname from partymaster where orgid=?1 and active=1 group by partyname")
	Set<Object[]> findAllCustomers(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getTmsJobCardDocId(Long orgId, String finYear, String branchCode, String screenCode);


}
