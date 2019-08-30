package com.zdz.system.controller;

import com.zdz.common.entity.Result;
import com.zdz.common.entity.ResultCode;
import com.zdz.domain.system.Permission;
import com.zdz.domain.system.User;
import com.zdz.system.service.PermissionService;
import com.zdz.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/system")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception{
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }


    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) throws Exception{
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }

    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody Map<String,Object> map) throws Exception{
        map.put("id",id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") String id) throws Exception{
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

}
