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
    			+ "        AND a.accountcode = b.clientcoacode\r\n"
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
    }

}

