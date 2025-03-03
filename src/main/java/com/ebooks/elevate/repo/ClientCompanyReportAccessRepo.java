package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ClientCompanyReportAccessVO;
import com.ebooks.elevate.entity.ClientCompanyVO;

public interface ClientCompanyReportAccessRepo extends JpaRepository<ClientCompanyReportAccessVO, Long> {

	List<ClientCompanyReportAccessVO> findByClientCompanyVO(ClientCompanyVO clientCompanyVO);

}
