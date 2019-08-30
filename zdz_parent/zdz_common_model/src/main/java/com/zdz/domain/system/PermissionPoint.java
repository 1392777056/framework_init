package com.zdz.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IDEA
 * Description: 菜单权限实体类
 */
@Entity
@Table(name = "tb_permission_point")
@Getter
@Setter
public class PermissionPoint implements Serializable {

    private static final long serialVersionUID = 504608330250006441L;
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 权限代码
     */
    @Column(name = "point_class")
    private String pointClass;

    @Column(name = "point_icon")
    private String pointIcon;

    @Column(name = "point_status")
    private String pointStatus;

}