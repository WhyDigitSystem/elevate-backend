package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebooks.elevate.entity.ArApOffSetInvoiceDetailsVO;
@Repository
public interface ArApOffSetInvoiceDetailsRepo extends JpaRepository<ArApOffSetInvoiceDetailsVO, Long>{

}
