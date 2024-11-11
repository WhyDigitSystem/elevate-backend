package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ArApAdjustmentOffSetVO;

@Repository
public interface ArApAdjustmentOffSetRepo extends JpaRepository<ArApAdjustmentOffSetVO, Long>{

	@Query(nativeQuery = true,value = "select * from arapadjustmentoffset where orgid=?1")
	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from arapadjustmentoffset where arapadjustmentoffsetid=?1")
	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetById(Long id);

	@Query(nativeQuery = true,value = "select * from arapadjustmentoffset where active =1")
	List<ArApAdjustmentOffSetVO> findArApAdjustmentOffSetByActive();

}
