package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.DailyMonthlyExRatesVO;

public interface DailyMonthlyExRatesRepo extends JpaRepository<DailyMonthlyExRatesVO, Long> {

	@Query(nativeQuery = true,value = "select * from dailymonthlyexrates where dailymonthlyexratesid=?1")
	List<DailyMonthlyExRatesVO> findDailyMonthlyExRatesById(Long id);

	@Query(nativeQuery = true,value = "select * from dailymonthlyexrates where orgid=?1")
	List<DailyMonthlyExRatesVO> findDailyMonthlyExRatesByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "select * from dailymonthlyexrates where active=1")
	List<DailyMonthlyExRatesVO> findDailyMonthlyExRatesByActive();

}
