package com.zdz.system.service;

import com.zdz.common.entity.ResultCode;
import com.zdz.common.exception.CommonException;
import com.zdz.common.utils.BeanMapUtils;
import com.zdz.common.utils.IdWorker;
import com.zdz.common.utils.PermissionConstants;
import com.zdz.domain.system.Permission;
import com.zdz.domain.system.PermissionApi;
import com.zdz.domain.system.PermissionMenu;
import com.zdz.domain.system.PermissionPoint;
import com.zdz.system.dao.PermissionApiDao;
import com.zdz.system.dao.PermissionDao;
import com.zdz.system.dao.PermissionMenuDao;
import com.zdz.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Map<String,Object> map) throws Exception {
        String id = String.valueOf(idWorker.nextId());
        //1.通过map构造权限对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        //2.根据类型构造不同的资源对象
        int type = permission.getType();
        switch (type){
            case PermissionConstants.TB_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.TB_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.TB_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //3.保存
        permissionDao.save(permission);
    }

    public void update(Map<String,Object> map) throws Exception{
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        Permission perm = permissionDao.findById(permission.getId()).get();
        perm.setName(permission.getName());
        perm.setCode(permission.getCode());
        perm.setDescription(permission.getDescription());
        perm.setEnVisible(permission.getEnVisible());
        //2.根据类型构造不同的资源对象
        int type = permission.getType();
        switch (type){
            case PermissionConstants.TB_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(permission.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.TB_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(permission.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.TB_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(permission.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //3.保存
        permissionDao.save(permission);
    }

    public Map<String, Object> findById(String id) throws Exception{
        Permission perm = permissionDao.findById(id).get();
        int type = perm.getType();

        Object object = null;

        if(type == PermissionConstants.TB_MENU) {
            object = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.TB_POINT) {
            object = permissionPointDao.findById(id).get();
        }else if (type == PermissionConstants.TB_API) {
            object = permissionApiDao.findById(id).get();
        }else {
            throw new CommonException(ResultCode.FAIL);
        }

        Map<String, Object> map = BeanMapUtils.beanToMap(object);

        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());


        return map;
    }

    public List<Permission> findAll(Map<String,Object> map){

        Specification<Permission> spec = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据父id查询
                if(!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class),(String)map.get("pid")));
                }
                //根据enVisible查询
                if(!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),(String)map.get("enVisible")));
                }
                //根据类型 type
                if(!StringUtils.isEmpty(map.get("type"))) {
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if("0".equals(ty)) {
                        in.value(1).value(2);
                    }else{
                        in.value(Integer.parseInt(ty));
                    }
                    list.add(in);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }

    public void deleteById(String id) throws Exception{
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        //2.根据类型构造不同的资源
        int type = permission.getType();
        switch (type) {
            case PermissionConstants.TB_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.TB_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.TB_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }


}
