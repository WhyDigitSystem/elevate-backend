package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.CoaVO;

public interface CoaRepo extends JpaRepository<CoaVO, Long>{

	boolean existsByAccountCode(String accountCode);

	boolean existsByAccountGroupName(String accountGroupName);


	CoaVO findByAccountGroupName(String groupName);

	@Query(nativeQuery =true,value ="select accountgroupname from  coa where active=1 and type='group'")
	Set<Object[]> findGroups();

	@Query(nativeQuery = true,value = "select a.accountgroupname maingroup,a.accountcode maingroupaccountcode,b.accountgroupname subgroup,b.accountcode subgroupaccountcode,c.accountgroupname account,c.accountcode accountcode from coa a, coa b,coa c where b.parentcode=a.accountcode and c.parentcode= b.accountcode  \r\n"
			+ "group by a.accountgroupname,b.accountgroupname,c.accountgroupname,a.accountcode,b.accountcode,c.accountcode order by a.accountcode,b.accountcode,c.accountcode asc")
	Set<Object[]> findAccountMap();

	CoaVO findByOrgIdAndAccountCode(Long orgId, String elAccountCode);




}
