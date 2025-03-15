package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.GroupMappingVO;

public interface GroupMappingRepo extends JpaRepository<GroupMappingVO, Long> {

	boolean existsByOrgIdAndGroupNameIgnoreCase(Long orgId, String groupName);

	@Query(nativeQuery =true,value ="select * from groupmapping where orgid=?1")
	List<GroupMappingVO> getGroupMappingAll(Long orgId);

	@Query(nativeQuery = true,value = "select c.accountgroupname,c.accountcode from groupmapping a, subgroup b,coa c where a.groupmappingid=b.groupmappingid and a.orgid=?1 and a.groupname=?2 \r\n"
			+ " and b.active=1 and a.orgid=c.orgid and b.accountcode=c.accountcode and c.active=1 group by\r\n"
			+ " c.accountcode,c.accountgroupname")
	Set<Object[]> getSubGroupDetails(Long orgId, String mainGroup);
	
	
	@Query(nativeQuery = true,value = "select c.accountgroupname,c.accountcode,c.natureofaccount from groupmapping a, subgroup b,coa c,groupledgers d where a.groupmappingid=b.groupmappingid \r\n"
			+ " and a.orgid=?1 and a.groupname=?2 and b.accountcode=?3 and b.accountcode=d.subgroupcode and d.active=1 and c.orgid=d.orgid and b.accountcode=c.parentcode\r\n"
			+ " and b.active=1 and a.orgid=c.orgid and c.accountcode=d.accountcode and c.active=1 group by\r\n"
			+ " c.accountgroupname,c.accountcode,c.natureofaccount")
	Set<Object[]> getGroupLedgersDetails(Long orgId, String mainGroup,String accountCode);
}
