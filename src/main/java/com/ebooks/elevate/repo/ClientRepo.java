package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ClientVO;
@Repository
public interface ClientRepo extends JpaRepository<ClientVO, Long>{

	boolean existsByClientCodeAndId(String clientCode, Long id);

	boolean existsByClientAndId(String client, Long id);

	boolean existsByClientMailAndId(String clientMail, Long id);

	boolean existsByPhoneNoAndId(String phoneNo, Long id);


}
