package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ElMfrVO;

@Repository
public interface ElMfrRepo extends JpaRepository<ElMfrVO, Long> {

	boolean existsByDescriptionAndOrgId(String description, Long orgId);

	boolean existsByElCodeAndOrgId(String elCode, Long orgId);

	@Query(nativeQuery = true, value = "select * FROM elmfr where orgid=?1")
	List<ElMfrVO> getAllElMfr(Long orgId);

}
