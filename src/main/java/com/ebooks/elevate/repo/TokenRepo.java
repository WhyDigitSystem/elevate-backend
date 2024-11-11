package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.TokenVO;

public interface TokenRepo extends JpaRepository<TokenVO, String>{

}
