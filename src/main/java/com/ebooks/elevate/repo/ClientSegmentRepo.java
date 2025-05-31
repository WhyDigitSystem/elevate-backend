package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ClientCompanyVO;
import com.ebooks.elevate.entity.ClientSegmentVO;

public interface ClientSegmentRepo extends JpaRepository<ClientSegmentVO, Long> {

	List<ClientSegmentVO> findByClientCompanyVO(ClientCompanyVO clientCompanyVO);

}
