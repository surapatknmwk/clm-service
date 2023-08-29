package com.ktb.clmapiauthen.repository;

import org.springframework.stereotype.Repository;
import com.ktb.clmapiauthen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmpMailAndEmpNameEng(String email, String name);
    User findByEmpMail(String email);
}
