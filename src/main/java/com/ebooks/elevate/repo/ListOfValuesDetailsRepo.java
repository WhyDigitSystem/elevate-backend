package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ListOfValuesDetailsVO;
import com.ebooks.elevate.entity.ListOfValuesVO;

public interface ListOfValuesDetailsRepo extends JpaRepository<ListOfValuesDetailsVO, Long>{


	List<ListOfValuesDetailsVO> findByListOfValuesVO(ListOfValuesVO listOfValuesVO);

}
