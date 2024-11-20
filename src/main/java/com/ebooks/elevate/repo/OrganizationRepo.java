package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.OrganizationVO;

@Repository
public interface OrganizationRepo extends JpaRepository<OrganizationVO, Long>{

}
