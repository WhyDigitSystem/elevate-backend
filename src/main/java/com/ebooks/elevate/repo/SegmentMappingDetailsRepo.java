package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.SegmentMappingDetailsVO;
import com.ebooks.elevate.entity.SegmentMappingVO;

public interface SegmentMappingDetailsRepo extends JpaRepository<SegmentMappingDetailsVO, Long> {

	List<SegmentMappingDetailsVO> findBySegmentMappingVO(SegmentMappingVO segmentMappingVO);

}
