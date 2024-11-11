package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.DocumentTypeVO;

public interface DocumentTypeRepo extends JpaRepository<DocumentTypeVO, Long>{

	

	boolean existsByOrgIdAndScreenCode(Long orgId, String screenCode);

	boolean existsByOrgIdAndDocCode(Long orgId, String docCode);

	List<DocumentTypeVO> findAllByOrgId(Long orgId);

}
