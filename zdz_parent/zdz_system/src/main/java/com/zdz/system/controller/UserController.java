package com.zdz.system.controller;

import com.zdz.common.entity.PageResult;
import com.zdz.common.entity.Result;
import com.zdz.common.entity.ResultCode;
import com.zdz.domain.system.User;
import com.zdz.system.service.RoleService;
import com.zdz.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/system")
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map){
        String id = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.assignRoles(id,roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map){
        Page pageUser = userService.findAll(map, page, size);
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }


    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

}
