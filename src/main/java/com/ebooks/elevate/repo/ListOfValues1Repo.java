package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ListOfValues1VO;
import com.ebooks.elevate.entity.ListOfValuesVO;

@Repository
public interface ListOfValues1Repo extends JpaRepository<ListOfValues1VO, Long>{

	List<ListOfValues1VO> findBylistOfValuesVO(ListOfValuesVO listOfValuesVO);

}
