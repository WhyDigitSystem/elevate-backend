package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.TrialBalanceVO;

public interface TrialBalanceRepo extends JpaRepository<TrialBalanceVO, Long> {

	@Query(nativeQuery =true,value ="select b.coacode,b.coa,a.accountcode,a.accountname,a.debit,a.credit from tbexcelupload a, ledgermapping b where a.orgid=?1 and a.clientcode=?3 and a.month=?4 and a.finyear=?2 \r\n"
			+ "and a.orgid=b.orgid and a.clientcode=b.clientcode and a.accountcode=b.clientcoacode")
	Set<Object[]> getFillGridForTbExcelUpload(Long orgId, String finYear, String clientCode, String tbMonth);


}
