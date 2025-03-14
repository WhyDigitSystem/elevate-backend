package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.ListOfValuesVO;

public interface ListOfValuesRepo extends JpaRepository<ListOfValuesVO, Long>{

	List<ListOfValuesVO> findAllByOrgId(Long orgId);

	List<ListOfValuesVO> getAllById(Long id);

	boolean existsByNameAndOrgId(String name, Long orgId);

	@Query(nativeQuery = true, value = "select valuesdescription from listofvaluesdetails a, listofvalues b where a.listofvaluesid=b.listofvaluesid and b.orgid=?1 and b.name=?2 and a.active=1\r\n"
			+ "group by valuesdescription")
	Set<Object[]> getListValuesDetailsForBudget(Long orgId,String name);

}
