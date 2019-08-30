package com.zdz.system.service;

import com.zdz.common.utils.IdWorker;
import com.zdz.common.utils.PermissionConstants;
import com.zdz.domain.system.Permission;
import com.zdz.domain.system.Role;
import com.zdz.system.dao.PermissionDao;
import com.zdz.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 分配权限
     */
    public void assignPerms(String roleId, List<String> permIds) {
        //1.获取分配的角色对象
        Role role = roleDao.findById(roleId).get();
        //2.构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.TB_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        System.out.println(perms.size());
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        //4.更新角色
        roleDao.save(role);
    }

    public void save(Role Role){
        String id = String.valueOf(idWorker.nextId());
        Role.setId(id);
        roleDao.save(Role);
    }

    public void update(Role Role){
        Role target = roleDao.findById(Role.getId()).get();
        roleDao.save(target);
    }

    public Role findById(String id){
        return roleDao.findById(id).get();
    }

    public Page<Role> findAll(Map<String,Object> map,int page,int size){

        Page<Role> pageRole = roleDao.findAll(new PageRequest(page-1, size));
        return pageRole;
    }

    public void deleteById(String id){
        roleDao.deleteById(id);
    }


}
