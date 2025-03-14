package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.GroupMappingVO;

public interface GroupMappingRepo extends JpaRepository<GroupMappingVO, Long> {

	boolean existsByOrgIdAndGroupNameIgnoreCase(Long orgId, String groupName);

}
