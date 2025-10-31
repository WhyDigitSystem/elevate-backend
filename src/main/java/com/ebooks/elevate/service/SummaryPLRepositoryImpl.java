package com.ebooks.elevate.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SummaryPLRepositoryImpl implements SummaryPLService {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
    public Set<Object[]> getELSummaryReport(String clientCode, String finYear, String previousYear, String month) {
        try {
            // Read SQL file content
            String sql = new String(Files.readAllBytes(Paths.get("C:/SummaryReportQuery/summaryPL.sql")));

            // Replace parameters
            sql = sql.replace("?1", "'" + clientCode + "'")
                     .replace("?2", "'" + finYear + "'")
                     .replace("?3", "'" + previousYear + "'")
                     .replace("?4", "'" + month + "'");

            // Execute query
            List<Object[]> resultList = entityManager.createNativeQuery(sql).getResultList();

            return new HashSet<>(resultList);
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL file", e);
        }
    }

	@Override
	public Set<Object[]> getELSummaryCashFlowReport(String clientCode, String finYear, String previousYear,
			String month) {
		try {
            // Read SQL file content
            String sql = new String(Files.readAllBytes(Paths.get("C:/SummaryReportQuery/summaryCFS.sql")));

            // Replace parameters
            sql = sql.replace("?1", "'" + clientCode + "'")
                     .replace("?2", "'" + finYear + "'")
                     .replace("?3", "'" + previousYear + "'")
                     .replace("?4", "'" + month + "'");

            // Execute query
            List<Object[]> resultList = entityManager.createNativeQuery(sql).getResultList();

            return new HashSet<>(resultList);
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL file", e);
        }
	}

}
