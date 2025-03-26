package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.FinancialYearVO;

public interface FinancialYearRepo extends JpaRepository<FinancialYearVO, Long> {
	@Query(nativeQuery = true, value = "select * from finyear where company=?1")
	List<FinancialYearVO> findFinyearByCompany(String company);

	@Query(value = "select * from financialyear where orgid=?1", nativeQuery = true)
	List<FinancialYearVO> findFinancialYearByOrgId(Long orgId);

	@Query(value = "select * from financialyear a where  a.active=1 and a.closed=0 and a.orgid=?1",nativeQuery = true)
	List<FinancialYearVO> findAllActiveFinYear(Long orgId);

	boolean existsByFinYearAndOrgId(int finYear, Long orgId);

	boolean existsByFinYearIdentifierAndOrgId(String finYearIdentifier, Long orgId);

	boolean existsByFinYearIdAndOrgId(Long finYearId, Long orgId);

	@Query(nativeQuery = true,value = "select b.finyearidentifier from clientcompany a,financialyear b where a.orgid=b.orgid and a.clientcode=?2 and a.orgid=?1 and a.clientyear=b.yeartype\r\n"
			+ "order by b.finyearidentifier desc")
	Set<Object[]> getClientFinYear(Long orgId, String clientCode);

	FinancialYearVO findByOrgIdAndFinYearIdentifier(Long orgId, String finyear);

	FinancialYearVO findByOrgIdAndFinYear(Long orgId, int previousYear);

}