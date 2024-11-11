package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.IrnCreditChargesVO;
import com.ebooks.elevate.entity.IrnCreditVO;

@Repository
public interface IrnCreditChargesRepo extends JpaRepository<IrnCreditChargesVO, Long>{

	List<IrnCreditChargesVO> findByIrnCreditVO(IrnCreditVO irnCreditVO);


}
