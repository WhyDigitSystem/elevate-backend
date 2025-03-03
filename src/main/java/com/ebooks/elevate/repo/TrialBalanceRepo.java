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

	
	@Query(nativeQuery = true, value = "select row_number() over()id,a.accountcode,a.accountname,a.coacode,a.coa,a.opbaldebit,a.opbalcredit,a.transdebit,a.transcredit,a.clbaldebit,a.clbalcredit,a.closingbalance,a.natureofaccount from vw_elytdtb a where  a.orgid=?1 and a.clientcode=?2 and finyear=?3 \r\n"
			+ "	and month=?4")
	Set<Object[]> getElYTDTbdetails(Long orgId, String clientCode, String finyear, String month);
	
	@Query(nativeQuery = true, value = "SELECT  \r\n"
			+ "    ROW_NUMBER() OVER() AS id,  \r\n"
			+ "    a.accountcode,  \r\n"
			+ "    a.accountname,  \r\n"
			+ "    a.coacode,  \r\n"
			+ "    a.coa,  \r\n"
			+ "    a.natureofaccount,  \r\n"
			+ "    a.closingbalance,  \r\n"
			+ "    b.amount AS budget,  \r\n"
			+ "    c.amount AS PYActual ,\r\n"
			+ "    CASE  \r\n"
			+ "        WHEN (a.natureofaccount = 'Cr' AND a.closingbalance > 0) THEN 'Yes'  \r\n"
			+ "        WHEN (a.natureofaccount = 'Db' AND a.closingbalance < 0) THEN 'Yes'  \r\n"
			+ "        ELSE 'No'  \r\n"
			+ "    END AS mismatch\r\n"
			+ "FROM vw_elytdtb a  \r\n"
			+ "LEFT JOIN budget b  \r\n"
			+ "    ON a.orgid = b.orgid  \r\n"
			+ "    AND a.clientcode = b.clientcode  \r\n"
			+ "    AND a.finyear = b.year  \r\n"
			+ "    AND a.month = b.month  \r\n"
			+ "    AND a.coacode = b.accountcode  \r\n"
			+ "LEFT JOIN previousyearactual c  \r\n"
			+ "    ON a.orgid = c.orgid  \r\n"
			+ "    AND a.clientcode = c.clientcode  \r\n"
			+ "    AND a.coacode = c.accountcode  \r\n"
			+ "    AND c.year = CAST(?3 AS UNSIGNED) - 1  \r\n"
			+ "    AND c.month = ?4  \r\n"
			+ "WHERE a.orgid = ?1  \r\n"
			+ "AND a.clientcode = ?2  \r\n"
			+ "AND a.finyear = ?3  \r\n"
			+ "ORDER BY a.accountcode ASC")
	Set<Object[]> getElYTDTbdetailsforMonthlyProcess(Long orgId, String clientCode, String finyear, String month);

}
