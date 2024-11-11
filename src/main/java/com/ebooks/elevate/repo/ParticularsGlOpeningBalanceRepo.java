package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.GlOpeningBalanceVO;
import com.ebooks.elevate.entity.ParticularsGlOpeningBalanceVO;
@Repository
public interface ParticularsGlOpeningBalanceRepo extends JpaRepository<ParticularsGlOpeningBalanceVO,Long> {

	List<ParticularsGlOpeningBalanceVO> findByGlOpeningBalanceVO(GlOpeningBalanceVO glOpeningBalanceVO);

}
