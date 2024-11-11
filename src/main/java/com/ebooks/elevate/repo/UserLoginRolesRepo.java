package com.ebooks.elevate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.UserLoginRolesVO;
import com.ebooks.elevate.entity.UserVO;


public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

}