package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.SetTaxRateVO;

@Repository
public interface SetTaxRateRepo extends JpaRepository<SetTaxRateVO, Long>{
 
	
	@Query(value="Select * from settaxrate where orgid=?1",nativeQuery = true)
	List<SetTaxRateVO> getAllSetTaxRateByOrgId(Long orgId);
    
	@Query(value="Select * from settaxrate where settaxrateid=?1",nativeQuery = true)
	List<SetTaxRateVO> getAllSetTaxRateById(Long id);

	@Query(nativeQuery = true,value = "select * from settaxrate where active=1")
	List<SetTaxRateVO> findSetTaxRateByActive();

	boolean existsByChapterAndOrgId(String chapter, Long orgId);

	boolean existsByHsnCodeAndOrgId(String hsnCode, Long orgId);

    
	
}
