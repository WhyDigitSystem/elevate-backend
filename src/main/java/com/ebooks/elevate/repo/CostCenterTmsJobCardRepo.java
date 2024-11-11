package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.CostCenterTmsJobCardVO;
import com.ebooks.elevate.entity.TmsJobCardVO;
@Repository
public interface CostCenterTmsJobCardRepo extends JpaRepository<CostCenterTmsJobCardVO, Long> {

	List<CostCenterTmsJobCardVO> findByTmsJobCardVO(TmsJobCardVO tmsJobCardVO);

}
