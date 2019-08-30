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
@Table(name = "tb_permission_menu")
@Getter
@Setter
public class PermissionMenu implements Serializable {

    private static final long serialVersionUID = -2819115658294515732L;
    /**
     * 主键
     */
    @Id
    private String id;

    //展示图标
    @Column(name = "menu_icon")
    private String menuIcon;

    //排序号
    @Column(name = "menu_order")
    private String menuOrder;
}