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

	
	@Query(nativeQuery =true,value ="SELECT tb.accountcode,tb.accountname,'Pedning Client COA Creation' action from tbexcelupload tb where tb.clientcode=?2  and orgid=?1 \r\n"
			+ "       and tb.accountcode not in(select accountcode from ccoa where clientcode=?2 and orgid=?1 group by accountcode) and tb.accountcode Not in \r\n"
			+ "			(select l.clientcoacode from ledgermapping l where l.clientcode=?2  and orgid=?1 group \r\n"
			+ "			by l.clientcoacode)\r\n"
			+ "			group by tb.accountcode,tb.accountname,action\r\n"
			+ "            union\r\n"
			+ "            SELECT tb.accountcode,tb.accountname,'Pedning Ledger Mapping' action from tbexcelupload tb where tb.clientcode=?2  and orgid=?1 \r\n"
			+ "       and tb.accountcode in(select accountcode from ccoa where clientcode=?2 and orgid=?1 group by accountcode) and tb.accountcode Not in \r\n"
			+ "			(select l.clientcoacode from ledgermapping l where l.clientcode=?2  and orgid=?1 group \r\n"
			+ "			by l.clientcoacode)\r\n"
			+ "			group by tb.accountcode,tb.accountname,action")
	Set<Object[]> getMisMatchClientTb(Long orgId, String clientCode);

}
