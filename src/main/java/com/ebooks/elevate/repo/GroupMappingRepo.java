package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.GroupMappingVO;

public interface GroupMappingRepo extends JpaRepository<GroupMappingVO, Long> {

	boolean existsByOrgIdAndGroupNameIgnoreCase(Long orgId, String groupName);

	@Query(nativeQuery =true,value ="select * from groupmapping where orgid=?1")
	List<GroupMappingVO> getGroupMappingAll(Long orgId);

}
