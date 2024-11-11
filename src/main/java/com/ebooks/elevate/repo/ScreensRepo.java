package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ResponsibilityVO;
import com.ebooks.elevate.entity.ScreensVO;

public interface ScreensRepo extends JpaRepository<ScreensVO, Long> {

	List<ScreensVO> findByResponsibilityVO(ResponsibilityVO responsibilityVO);

}
