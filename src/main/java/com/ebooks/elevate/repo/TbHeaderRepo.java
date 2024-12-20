package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.TbHeaderVO;

@Repository
public interface TbHeaderRepo extends JpaRepository<TbHeaderVO, Long>{

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2  and screencode=?3")
	String getTBDocId(Long orgId, String finYear, String screenCode);

}
