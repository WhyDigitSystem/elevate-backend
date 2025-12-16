package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.TbHistoryVO;

public interface TbHistoryRepo extends JpaRepository<TbHistoryVO, Long> {

}
