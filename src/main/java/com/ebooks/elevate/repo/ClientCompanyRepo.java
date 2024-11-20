package com.ebooks.elevate.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ClientCompanyVO;

@Repository
public interface ClientCompanyRepo extends JpaRepository<ClientCompanyVO, Long>{

	@Query(nativeQuery = true, value = "select * from clientcompany  where orgid=?1")
	Optional<ClientCompanyVO> findClientCompanyByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from clientcompany  where clientcompanyid=?1")
	Optional<ClientCompanyVO> findByClientCompanyById(Long companyid);

	boolean existsByClientCodeAndOrgId(String companyCode, Long id);

	boolean existsByWebSiteAndOrgId(String webSite, Long id);

	boolean existsByPhoneAndOrgId(String phone, Long id);

	boolean existsByEmailAndOrgId(String email, Long id);

	boolean existsByClientNameAndOrgId(String companyName, Long id);



}
