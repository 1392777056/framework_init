package com.zdz.system.dao;

import com.zdz.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
}
