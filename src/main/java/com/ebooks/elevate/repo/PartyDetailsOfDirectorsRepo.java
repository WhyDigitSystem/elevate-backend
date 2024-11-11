package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyDetailsOfDirectorsVO;
import com.ebooks.elevate.entity.PartyMasterVO;

@Repository
public interface PartyDetailsOfDirectorsRepo extends JpaRepository<PartyDetailsOfDirectorsVO, Long>{

	List<PartyDetailsOfDirectorsVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
