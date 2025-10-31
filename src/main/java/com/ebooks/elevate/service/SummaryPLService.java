package com.ebooks.elevate.service;

import java.util.Set;

public interface SummaryPLService {
	
	Set<Object[]> getELSummaryReport(String clientCode, String finYear, String previousYear, String month);
	
	Set<Object[]> getELSummaryCashFlowReport(String clientCode, String finYear, String previousYear, String month);
}
