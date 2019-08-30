package com.zdz.system.dao;

import com.zdz.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    public User findByMobile(String mobile);
}
