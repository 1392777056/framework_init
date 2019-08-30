package com.zdz.system.dao;

import com.zdz.domain.system.PermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionPointDao extends JpaRepository<PermissionPoint,String>, JpaSpecificationExecutor<PermissionPoint> {
}
