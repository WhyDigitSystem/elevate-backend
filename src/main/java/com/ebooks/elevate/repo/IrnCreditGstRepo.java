package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.IrnCreditGstVO;
import com.ebooks.elevate.entity.IrnCreditVO;
@Repository
public interface IrnCreditGstRepo extends JpaRepository<IrnCreditGstVO,Long>{

	List<IrnCreditGstVO> findByIrnCreditVO(IrnCreditVO irnCreditVO);

}
