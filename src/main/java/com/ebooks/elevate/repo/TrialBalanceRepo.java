package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.TrialBalanceVO;

public interface TrialBalanceRepo extends JpaRepository<TrialBalanceVO, Long> {

	@Query(nativeQuery =true,value ="select m.accountcode,m.accountname,n.coacode,n.coa, m.opbalancedb,m.opbalancecr,m.transdebit,m.transcredit,m.clbalancedb,m.clbalancecr,row_number() over() from\r\n"
			+ "(select a.accountcode accountcode,a.accountname, a.opbalancedb,a.opbalancecr,a.transdebit,a.transcredit,a.clbalancedb,a.clbalancecr,a.client,a.clientcode from tbexcelupload a \r\n"
			+ "where  a.finyear=?2 and a.month=?3   and a.orgid=?1 and a.client=?4 and a.clientcode=?5)m left join ledgermapping n on \r\n"
			+ "m.accountcode=n.clientcoacode and n.orgid=?1 and n.clientname=?4 and n.clientcode=?5")
	Set<Object[]> getFillGridForTbExcelUpload(Long orgId, String finYear,String tbMonth, String client,String clientCode );

	
	@Query(nativeQuery = true, value = "select row_number() over()id,b.coacode,b.coa,sum(a.opbalancedb)OPBalDebit,sum(a.opbalancecr)OPBalCredit,sum(a.transdebit)TransDebit,sum(a.transcredit)TransCredit,\r\n"
			+ "sum(a.clbalancedb)CLBalDebit,sum(a.clbalancecr)CLBalCredit from tbexcelupload a , ledgermapping b where a.orgid=?1 and a.clientcode=?2 and finyear=?3 \r\n"
			+ "and month=?4 \r\n"
			+ "and a.orgid=b.orgid and a.clientcode=b.clientcode and a.accountcode=b.clientcoacode group by b.coacode,b.coa")
	Set<Object[]> getElYTDTbdetails(Long orgId, String clientCode, String finyear, String month);


}
