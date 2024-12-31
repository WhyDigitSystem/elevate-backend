package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.FirstDataVO;

@Repository
public interface FirstDataRepo  extends JpaRepository<FirstDataVO, Long>{

}
