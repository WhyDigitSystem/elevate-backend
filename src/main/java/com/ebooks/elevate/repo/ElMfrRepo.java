package com.ebooks.elevate.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ElMfrVO;

@Repository
public interface ElMfrRepo extends JpaRepository<ElMfrVO, Long> {

	boolean existsByDescriptionAndOrgId(String description, Long orgId);

	boolean existsByElCodeAndOrgId(String elCode, Long orgId);

	@Query(nativeQuery = true, value = "select * FROM elmfr where orgid=?1")
	List<ElMfrVO> getAllElMfr(Long orgId);

	
	@Query(nativeQuery =true,value ="SELECT tb.accountcode,tb.accountname from tbexcelupload tb where tb.clientcode=?2 and tb.accountcode=?3 and orgid=?1 Not in\r\n"
			+ "(select l.clientcoacode from ledgermapping l where l.clientcode=?2 and l.clientcoacode=?3R and orgid=?1 group \r\n"
			+ "by l.clientcoacode)\r\n"
			+ "group by tb.accountcode,tb.accountname; ")
	Set<Object[]> getMisMatchClientTb(Long orgId, String clientCode, String accountCode);

}
