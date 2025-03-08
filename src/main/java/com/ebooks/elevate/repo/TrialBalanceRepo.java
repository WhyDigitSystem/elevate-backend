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

	
	@Query(nativeQuery = true, value = "select row_number() over()id,a.accountcode,a.accountname,a.coacode,a.coa,a.opbaldebit,a.opbalcredit,a.transdebit,a.transcredit,a.clbaldebit,a.clbalcredit,a.closingbalance,a.natureofaccount,b.groupname from vw_elytdtb a,coa b where a.coacode=b.accountcode and  a.orgid=?1 and a.clientcode=?2 and finyear=?3 \r\n"
			+ "	and month=?4")
	Set<Object[]> getElYTDTbdetails(Long orgId, String clientCode, String finyear, String month);
	
	@Query(nativeQuery = true, value = "WITH params AS (\r\n"
			+ "    SELECT \r\n"
			+ "        ?3 AS currentFinYear,\r\n"
			+ "        ?4 AS currentMonth\r\n"
			+ "),\r\n"
			+ "prev_params AS (\r\n"
			+ "    SELECT \r\n"
			+ "        CASE \r\n"
			+ "            WHEN currentMonth = 'January' THEN CAST(currentFinYear - 1 AS CHAR) \r\n"
			+ "            ELSE currentFinYear \r\n"
			+ "        END AS previousFinYear,\r\n"
			+ "        CASE \r\n"
			+ "            WHEN currentMonth = 'January' THEN 'December'\r\n"
			+ "            WHEN currentMonth = 'February' THEN 'January'\r\n"
			+ "            WHEN currentMonth = 'March' THEN 'February'\r\n"
			+ "            WHEN currentMonth = 'April' THEN 'March'\r\n"
			+ "            WHEN currentMonth = 'May' THEN 'April'\r\n"
			+ "            WHEN currentMonth = 'June' THEN 'May'\r\n"
			+ "            WHEN currentMonth = 'July' THEN 'June'\r\n"
			+ "            WHEN currentMonth = 'August' THEN 'July'\r\n"
			+ "            WHEN currentMonth = 'September' THEN 'August'\r\n"
			+ "            WHEN currentMonth = 'October' THEN 'September'\r\n"
			+ "            WHEN currentMonth = 'November' THEN 'October'\r\n"
			+ "            WHEN currentMonth = 'December' THEN 'November'\r\n"
			+ "        END AS previousMonth\r\n"
			+ "    FROM params\r\n"
			+ ")\r\n"
			+ "SELECT row_number() over() id,\r\n"
			+ "    h.coacode,\r\n"
			+ "    h.coa,\r\n"
			+ "    h.natureofaccount,\r\n"
			+ "    h.groupname,\r\n"
			+ "    SUM(h.currentmonth) AS currentMonthTotal,\r\n"
			+ "    SUM(h.previousmonth) AS previousMonthTotal,\r\n"
			+ "    SUM(h.currentmonth) - SUM(h.previousmonth) AS currentMonthActual,\r\n"
			+ "    SUM(h.budget) AS budget,\r\n"
			+ "    SUM(h.PYActual) AS PYActual, case when h.natureofaccount='Cr' and (SUM(h.currentmonth) - SUM(h.previousmonth)>0) then 'Yes'\r\n"
			+ "    when h.natureofaccount='Db' and (SUM(h.currentmonth) - SUM(h.previousmonth)<0) then 'Yes' else 'No' end missmatch,CASE   \r\n"
			+ "        WHEN  \r\n"
			+ "            (h.natureofaccount = 'Cr' AND (SUM(h.currentmonth) - SUM(h.previousmonth) > 0))  \r\n"
			+ "            OR  \r\n"
			+ "            (h.natureofaccount = 'Db' AND (SUM(h.currentmonth) - SUM(h.previousmonth) < 0))  \r\n"
			+ "        THEN 'No'  \r\n"
			+ "        ELSE 'Yes'  \r\n"
			+ "    END AS approveStatus \r\n"
			+ "FROM (\r\n"
			+ "    -- Fetch Current Month Data\r\n"
			+ "    SELECT \r\n"
			+ "        ROW_NUMBER() OVER() AS id,\r\n"
			+ "        a.coacode,\r\n"
			+ "        a.coa,\r\n"
			+ "        a.closingbalance AS currentmonth,\r\n"
			+ "        0 AS previousmonth,\r\n"
			+ "        a.natureofaccount,\r\n"
			+ "        b.groupname,\r\n"
			+ "        c.amount AS budget,\r\n"
			+ "        d.amount AS PYActual\r\n"
			+ "    FROM vw_elytdtb a \r\n"
			+ "    LEFT JOIN budget c  \r\n"
			+ "        ON a.orgid = c.orgid  \r\n"
			+ "        AND a.clientcode = c.clientcode  \r\n"
			+ "        AND a.finyear = c.year  \r\n"
			+ "        AND a.month = c.month  \r\n"
			+ "        AND a.coacode = c.accountcode \r\n"
			+ "        JOIN params ON a.finyear = params.currentFinYear AND a.month = params.currentMonth\r\n"
			+ "    LEFT JOIN previousyearactual d  \r\n"
			+ "        ON a.orgid = d.orgid  \r\n"
			+ "        AND a.clientcode = d.clientcode  \r\n"
			+ "        AND a.coacode = d.accountcode  \r\n"
			+ "        AND d.year = CAST(params.currentFinYear AS UNSIGNED) - 1\r\n"
			+ "    JOIN coa b ON a.coacode = b.accountcode\r\n"
			+ "    WHERE a.orgid = ?1\r\n"
			+ "    AND a.clientcode = ?2\r\n"
			+ "    \r\n"
			+ "    UNION ALL\r\n"
			+ "    \r\n"
			+ "    -- Fetch Previous Month Data Dynamically\r\n"
			+ "    SELECT \r\n"
			+ "        ROW_NUMBER() OVER() AS id,\r\n"
			+ "        a.coacode,\r\n"
			+ "        a.coa,\r\n"
			+ "        0 AS currentmonth,\r\n"
			+ "        a.closingbalance AS previousmonth,\r\n"
			+ "        a.natureofaccount,\r\n"
			+ "        b.groupname,\r\n"
			+ "        0 AS budget,\r\n"
			+ "        0 AS PYActual\r\n"
			+ "    FROM vw_elytdtb a\r\n"
			+ "    JOIN coa b ON a.coacode = b.accountcode\r\n"
			+ "    JOIN prev_params pp ON a.finyear = pp.previousFinYear AND a.month = pp.previousMonth\r\n"
			+ "    WHERE a.orgid = ?1\r\n"
			+ "    AND a.clientcode = ?2\r\n"
			+ ") h where (h.coacode, h.coa) not in(select a.accountcode,a.accountname from monthlyprocessdetails a, monthlyprocess b where a.monthlyprocessid=b.monthlyprocessid and  b.orgid=?1\r\n"
			+ "and a.year=?3 and a.month=?4 and a.clientcode=?2 group by a.accountcode,a.accountname)\r\n"
			+ "GROUP BY h.coacode, h.coa, h.natureofaccount, h.groupname")
	Set<Object[]> getElYTDTbdetailsforMonthlyProcess(Long orgId, String clientCode, String finyear, String month);

	

}
