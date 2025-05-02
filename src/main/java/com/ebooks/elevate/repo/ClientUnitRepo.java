package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.ClientCompanyVO;
import com.ebooks.elevate.entity.ClientUnitVO;

public interface ClientUnitRepo extends JpaRepository<ClientUnitVO, Long> {

	List<ClientUnitVO> findByClientCompanyVO(ClientCompanyVO clientCompanyVO);

}
