package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartyTdsExemptedVO;

@Repository
public interface PartyTdsExemptedRepo extends JpaRepository<PartyTdsExemptedVO, Long> {

	List<PartyTdsExemptedVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
