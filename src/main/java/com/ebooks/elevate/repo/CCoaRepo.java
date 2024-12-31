package com.ebooks.elevate.repo;

import java.util.List;
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


	boolean existsByAccountCode(String accountCode);

	CCoaVO findByOrgIdAndAccountCode(Long orgId, String clientAccountCodes);

	boolean existsByOrgIdAndAccountNameAndClientCode(Long orgId, String accountName, String clientCode);

	boolean existsByOrgIdAndAccountCodeAndClientCode(Long orgId, String accountCode, String clientCode);

	@Query(value = "select a from CCoaVO a where a.orgId=?1 and a.clientCode=?2")
	List<CCoaVO> findAllByOrgIdAndClientCode(Long orgId, String clientCode);

}
