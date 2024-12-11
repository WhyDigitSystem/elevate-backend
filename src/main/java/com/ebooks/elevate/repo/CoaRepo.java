package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.CCoaVO;
import com.ebooks.elevate.entity.CoaVO;

public interface CoaRepo extends JpaRepository<CoaVO, Long>{

	boolean existsByAccountCode(String accountCode);

	boolean existsByAccountGroupName(String accountGroupName);


	CoaVO findByAccountGroupName(String groupName);

	@Query(nativeQuery =true,value ="select accountgroupname from  coa where active=1 and type='group'")
	Set<Object[]> findGroups();


	//Object getId();




}
