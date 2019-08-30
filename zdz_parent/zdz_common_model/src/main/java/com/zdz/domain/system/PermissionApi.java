package com.zdz.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * api资源
 */
@Entity
@Table(name = "tb_permission_api")
@Getter
@Setter
public class PermissionApi implements Serializable {

    private static final long serialVersionUID = -7359149052877626887L;
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 链接
     */
    @Column(name = "api_url")
    private String apiUrl;
    /**
     * 请求类型
     */
    @Column(name = "api_method")
    private String apiMethod;
    /**
     * 权限等级，1为通用接口权限，2为需校验接口权限
     */
    @Column(name = "api_level")
    private String apiLevel;
}