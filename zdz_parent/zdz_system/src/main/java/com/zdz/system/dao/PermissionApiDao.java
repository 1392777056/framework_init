package com.zdz.system.dao;

import com.zdz.domain.system.PermissionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionApiDao extends JpaRepository<PermissionApi,String>, JpaSpecificationExecutor<PermissionApi> {
}
