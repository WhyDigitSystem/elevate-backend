package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.GroupLedgersVO;
import com.ebooks.elevate.entity.GroupMappingVO;

public interface GroupLedgersRepo extends JpaRepository<GroupLedgersVO, Long> {

	List<GroupLedgersVO> findByGroupMappingVO(GroupMappingVO groupMappingVO);

}
