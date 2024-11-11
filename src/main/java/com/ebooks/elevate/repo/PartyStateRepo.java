package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.PartyMasterVO;
import com.ebooks.elevate.entity.PartyStateVO;

@Repository
public interface PartyStateRepo extends JpaRepository<PartyStateVO, Long>{


	List<PartyStateVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
