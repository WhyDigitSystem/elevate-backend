package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.TaxMasterVO;

@Repository
public interface TaxMasterRepo extends JpaRepository<TaxMasterVO,Long>{

	@Query(value="Select * from taxmaster where orgid=?1",nativeQuery = true)
	List<TaxMasterVO> getAllTaxMasterByOrgId(Long orgId);

	@Query(value="Select * from taxmaster where taxmasterid=?1",nativeQuery = true)
	List<TaxMasterVO> getAllTaxMasterById(Long id);

	@Query(nativeQuery = true,value = "select * from taxmaster where active=1")
	List<TaxMasterVO> findTaxMasterByActive();

}
