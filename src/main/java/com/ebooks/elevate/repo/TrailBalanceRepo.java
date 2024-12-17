package com.ebooks.elevate.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebooks.elevate.entity.TrailBalanceVO;

public interface TrailBalanceRepo extends JpaRepository<TrailBalanceVO, Long> {

	@Query(nativeQuery =true,value ="select t.accountcode,t.accountname,t.credit,t.debit from tbexcelupload t where finyear=?1 and clientcode=?2 and month=?3")
	Set<Object[]> getFillGridForTbExcelUpload(String finYear, String clientCode, String month);

}
