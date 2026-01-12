package com.ebooks.elevate.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Views {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        try {
            executeQueries();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    private void executeQueries() {
    
    	jdbcTemplate.execute("create or replace view vw_eltb as\r\n"
    			+ "SELECT \r\n"
    			+ "    a.coacode AS coacode,\r\n"
    			+ "    a.coa AS coa,\r\n"
    			+ "    a.orgid,\r\n"
    			+ "    a.clientcode,\r\n"
    			+ "    a.finyear,\r\n"
    			+ "    a.month,\r\n"
    			+ "    a.natureofaccount AS natureofaccount,\r\n"
    			+ "    SUM(a.opbalancedb) AS OPBalDebit,\r\n"
    			+ "    SUM(a.opbalancecr) AS OPBalCredit,\r\n"
    			+ "    SUM(a.transdebit) AS TransDebit,\r\n"
    			+ "    SUM(a.transcredit) AS TransCredit,\r\n"
    			+ "    SUM(a.clbalancedb) AS CLBalDebit,\r\n"
    			+ "    SUM(a.clbalancecr) AS CLBalCredit\r\n"
    			+ "FROM\r\n"
    			+ "    (SELECT \r\n"
    			+ "        a.orgid,\r\n"
    			+ "        a.clientcode,\r\n"
    			+ "        a.finyear,\r\n"
    			+ "        a.month,\r\n"
    			+ "        b.coacode AS coacode,\r\n"
    			+ "        b.coa AS coa,\r\n"
    			+ "        c.natureofaccount AS natureofaccount,\r\n"
    			+ "        a.opbalancedb AS opbalancedb,\r\n"
    			+ "        a.opbalancecr AS opbalancecr,\r\n"
    			+ "        a.transdebit AS transdebit,\r\n"
    			+ "        a.transcredit AS transcredit,\r\n"
    			+ "        a.clbalancedb AS clbalancedb,\r\n"
    			+ "        a.clbalancecr AS clbalancecr\r\n"
    			+ "    FROM\r\n"
    			+ "        tbexcelupload a\r\n"
    			+ "    JOIN ledgermapping b ON a.orgid = b.orgid \r\n"
    			+ "        AND a.clientcode = b.clientcode \r\n"
    			+ "        AND a.accountname = b.clientcoa\r\n"
    			+ "    JOIN coa c ON c.accountcode = b.coacode) a\r\n"
    			+ "GROUP BY \r\n"
    			+ "    a.coacode, \r\n"
    			+ "    a.coa, \r\n"
    			+ "    a.natureofaccount, \r\n"
    			+ "    a.orgid, \r\n"
    			+ "    a.clientcode, \r\n"
    			+ "    a.finyear, \r\n"
    			+ "    a.month\r\n"
    			+ "ORDER BY a.coacode");
    	
    	jdbcTemplate.execute("create or replace view vw_elytdtb as\r\n"
    			+ "SELECT\r\n"
    			+ "    a.accountcode AS accountcode,\r\n"
    			+ "    a.accountname AS accountname,\r\n"
    			+ "    a.coacode AS coacode,\r\n"
    			+ "    a.coa AS coa,\r\n"
    			+ "    a.orgid AS orgid,\r\n"
    			+ "    a.clientcode AS clientcode,\r\n"
    			+ "    a.finyear AS finyear,\r\n"
    			+ "    a.month AS month,\r\n"
    			+ "    a.natureofaccount AS natureofaccount,\r\n"
    			+ "    SUM(a.opbalancedb) AS OPBalDebit,\r\n"
    			+ "    SUM(a.opbalancecr) AS OPBalCredit,\r\n"
    			+ "    SUM(a.transdebit) AS TransDebit,\r\n"
    			+ "    SUM(a.transcredit) AS TransCredit,\r\n"
    			+ "    SUM(a.clbalancedb) AS CLBalDebit,\r\n"
    			+ "    SUM(a.clbalancecr) AS CLBalCredit,\r\n"
    			+ "    (SUM(a.clbalancedb) - SUM(a.clbalancecr)) AS closingbalance\r\n"
    			+ "FROM\r\n"
    			+ "(\r\n"
    			+ "    SELECT\r\n"
    			+ "        t.orgid,\r\n"
    			+ "        t.clientcode,\r\n"
    			+ "        t.finyear,\r\n"
    			+ "        t.month,\r\n"
    			+ "        t.accountcode,\r\n"
    			+ "        t.accountname,\r\n"
    			+ "        l.coacode,\r\n"
    			+ "        l.coa,\r\n"
    			+ "        c.natureofaccount,\r\n"
    			+ "        t.opbalancedb,\r\n"
    			+ "        t.opbalancecr,\r\n"
    			+ "        t.transdebit,\r\n"
    			+ "        t.transcredit,\r\n"
    			+ "        t.clbalancedb,\r\n"
    			+ "        t.clbalancecr\r\n"
    			+ "    FROM tbexcelupload t\r\n"
    			+ "    JOIN ledgermapping l\r\n"
    			+ "        ON t.orgid = l.orgid\r\n"
    			+ "       AND t.clientcode = l.clientcode\r\n"
    			+ "       AND t.accountname = l.clientcoa\r\n"
    			+ "    JOIN coa c\r\n"
    			+ "        ON c.accountcode = l.coacode\r\n"
    			+ ") a\r\n"
    			+ "GROUP BY\r\n"
    			+ "    a.orgid,\r\n"
    			+ "    a.clientcode,\r\n"
    			+ "    a.finyear,\r\n"
    			+ "    a.month,\r\n"
    			+ "    a.accountcode,\r\n"
    			+ "    a.accountname,\r\n"
    			+ "    a.coacode,\r\n"
    			+ "    a.coa,\r\n"
    			+ "    a.natureofaccount\r\n"
    			+ "ORDER BY a.coacode asc");
    }
    
}

