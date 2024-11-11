package com.ebooks.elevate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebooks.elevate.entity.UserLoginBranchAccessibleVO;


public interface UserLoginBranchAccessibleRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
