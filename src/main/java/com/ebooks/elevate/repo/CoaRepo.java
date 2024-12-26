package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.CoaVO;

public interface CoaRepo extends JpaRepository<CoaVO, Long>{



	@Query(nativeQuery =true,value ="select accountgroupname from  coa where active=1 and orgid=?1 and type='group'")
	Set<Object[]> findGroups(Long orgId);

	@Query(nativeQuery = true,value = "select a.accountgroupname maingroup,a.accountcode maingroupaccountcode,b.accountgroupname subgroup,b.accountcode subgroupaccountcode,c.accountgroupname account,c.accountcode accountcode from coa a, coa b,coa c where b.parentcode=a.accountcode and b.orgid=a.orgid and c.parentcode= b.accountcode and c.orgid=b.orgid  \r\n"
			+ " and a.orgid=?1 group by a.accountgroupname,b.accountgroupname,c.accountgroupname,a.accountcode,b.accountcode,c.accountcode ORDER BY CAST(a.accountcode AS UNSIGNED) ASC,CAST(b.accountcode AS UNSIGNED) ASC, CAST(c.accountcode AS UNSIGNED) ASC")
	Set<Object[]> findAccountMap(Long orgId);

	CoaVO findByOrgIdAndAccountCode(Long orgId, String elAccountCode);


	boolean existsByOrgIdAndAccountCode(Long orgId, String accountCode);

	@Query(value = "select * from coa a where a.orgid=?1",nativeQuery = true)
	List<CoaVO> getAllCaoByOrgId(Long orgId);

	@Query(nativeQuery =true,value ="select * from  coa where active=1 and orgid=?1 and accountgroupname=?2  and type='group' and groupname is null")
	CoaVO getOrgIdAndMainAccountGroupName(Long orgId, String groupName);

	@Query(nativeQuery =true,value ="select * from  coa where active=1 and orgid=?1 and accountgroupname=?2  and type='group' and groupname is not null")
	CoaVO getOrgIdAndSubAccountGroupName(Long orgId, String groupName);

}
