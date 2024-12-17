package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CCoaVO;

@Repository
public interface CCoaRepo extends JpaRepository<CCoaVO, Long>{


	CCoaVO findByAccountName(String groupName);

	@Query(nativeQuery =true,value ="select accountgroupname from  ccoa where active=1 and type='group'")
	Set<Object[]> findGroups();

	boolean existsByAccountNameAndClientCode(String accountName, String clientCode);

	boolean existsByAccountCodeAndClientCode(String accountCode, String clientCode);

	boolean existsByAccountCode(String accountCode);

}
