package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartySpecialTDSVO;

@Repository
public interface PartySpecialTDSRepo extends JpaRepository<PartySpecialTDSVO, Long>{

	List<PartySpecialTDSVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
