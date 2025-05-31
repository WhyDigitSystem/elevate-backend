package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.GroupMappingVO;
import com.ebooks.elevate.entity.SubGroupDetailsVO;

public interface SubGroupDetailsRepo  extends JpaRepository<SubGroupDetailsVO, Long>{

	List<SubGroupDetailsVO> findByGroupMappingVO(GroupMappingVO groupMappingVO);

}
