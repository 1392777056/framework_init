package com.zdz.system.service;

import com.zdz.common.utils.IdWorker;
import com.zdz.domain.system.Role;
import com.zdz.domain.system.User;
import com.zdz.system.dao.RoleDao;
import com.zdz.system.dao.UserDao;
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
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据mobile查询用户
     */
    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }

    public void save(User user){
        String id = String.valueOf(idWorker.nextId());
        user.setId(id);
        user.setPassword("123456");
        user.setEnableState(1);
        user.setCreateTime(new Date());
        userDao.save(user);
    }

    public void update(User user){
        User target = userDao.findById(user.getId()).get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setCreateTime(new Date());
        userDao.save(target);
    }

    public User findById(String id){
        return userDao.findById(id).get();
    }

    public Page<User> findAll(Map<String,Object> map,int page,int size){
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("id"))){
                    list.add(criteriaBuilder.equal(root.get("id").as(String.class),(String) map.get("id")));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<User> pageUser = userDao.findAll(spec, new PageRequest(page-1, size));
        return pageUser;
    }

    public void deleteById(String id){
        userDao.deleteById(id);
    }

    public void assignRoles(String userId,List<String> roleIds){
        User user = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        user.setRoles(roles);
        userDao.save(user);
    }
}
