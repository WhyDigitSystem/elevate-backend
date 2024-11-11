package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyAddressVO;
import com.ebooks.elevate.entity.PartyMasterVO;

@Repository
public interface PartyAddressRepo extends JpaRepository<PartyAddressVO, Long>{

	List<PartyAddressVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
