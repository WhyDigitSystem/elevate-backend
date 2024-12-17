package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.TbHeaderVO;

@Repository
public interface TbHeaderRepo extends JpaRepository<TbHeaderVO, Long>{

}
