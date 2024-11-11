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
    	
    	// Exrates View
    	jdbcTemplate.execute("create or replace view vw_exrates as\r\n"
    			+ "SELECT \r\n"
    			+ "    c.dailymonthlyexratesdtlid,\r\n"
    			+ "    g.orgid,\r\n"
    			+ "    g.date,\r\n"
    			+ "    g.month,\r\n"
    			+ "    c.currency, \r\n"
    			+ "    c.currencydescripition, \r\n"
    			+ "    c.buyingexrate, \r\n"
    			+ "    c.sellingexrate\r\n"
    			+ "FROM \r\n"
    			+ "    dailymonthlyexratesdtl c\r\n"
    			+ "JOIN \r\n"
    			+ "    dailymonthlyexrates g\r\n"
    			+ "ON \r\n"
    			+ "    c.dailymonthlyexratesid = g.dailymonthlyexratesid\r\n"
    			+ "JOIN (\r\n"
    			+ "    SELECT \r\n"
    			+ "        a.currency,\r\n"
    			+ "        MAX(a.dailymonthlyexratesdtlid) AS dailymonthlyexratesdtlid\r\n"
    			+ "    FROM \r\n"
    			+ "        dailymonthlyexratesdtl a\r\n"
    			+ "    JOIN \r\n"
    			+ "        dailymonthlyexrates b\r\n"
    			+ "    ON \r\n"
    			+ "        a.dailymonthlyexratesid = b.dailymonthlyexratesid\r\n"
    			+ "    GROUP BY \r\n"
    			+ "        a.currency\r\n"
    			+ ") d \r\n"
    			+ "ON c.dailymonthlyexratesdtlid = d.dailymonthlyexratesdtlid");
   }

}
