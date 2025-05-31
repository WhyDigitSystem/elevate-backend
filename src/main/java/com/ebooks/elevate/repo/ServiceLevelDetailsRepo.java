package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ServiceLevelDetailsVO;
import com.ebooks.elevate.entity.ServiceLevelVO;
@Repository
public interface ServiceLevelDetailsRepo extends JpaRepository<ServiceLevelDetailsVO, Long>{

	List<ServiceLevelDetailsVO> findByServiceLevelVO(ServiceLevelVO serviceLevelVO);

}
