package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.wiki.entity.ResponseData;
import org.wiki.pojo.Admin;
import org.wiki.pojo.User;
import org.wiki.service.IAdminService;
import org.wiki.service.IUserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wiki/system")
@Api(tags = "后台管理员的操作接口")
public class AdminController {
    @Reference
    private IAdminService adminService;
    @Reference
    private IUserService userService;
    @PostMapping("/login")
    @ApiOperation(value = "后台管理员登录接口", notes = "根据用户名和密码认证用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "登陆账号", paramType = "query", required = true, name = "manager_name", dataType = "String"),
            @ApiImplicitParam(value = "登陆密码", paramType = "query", required = true, name = "manager_password", dataType = "String")
    })
    public ResponseData<Admin> login(String manager_name, String manager_password){
        ResponseData responseData = null;
        Admin admin = adminService.selectByName(manager_name);
        if (admin == null){
            responseData = ResponseData.cusstomerError("用户不存在");
        }else {
//            boolean checkPw = BCrypt.checkpw(manager_password, admin.getPassword());
//            if (checkPw) {
//                String token = JWTUtils.generToken(admin.getId().toString(), "liuliu@Mall", admin.getName());
//                responseData = ResponseData.success().putDataVule("token", token).putDataVule("admin", admin);
//            } else {
//                responseData = ResponseData.cusstomerError("用户认证失败");
//            }
            responseData = ResponseData.success().putDataVule("admin",admin);
        }
        return responseData;
    }

    //    查询所有用户信息
    @GetMapping("/userList")
    @ApiOperation(value = "用户信息列表",notes = "获取所有用户信息")
    public ResponseData<User> userList(){
        List<User> users = userService.userList();
        if(users != null){
            return ResponseData.success().putDataVule("userList",users);
        }else{
            return ResponseData.serverInternalError();
        }
    }

    //    根据用户的用户名和状态查询用户信息
    @PostMapping("/userQuery")
    @ApiOperation(value = "条件查询用户信息", notes = "根据用户名和状态查询用户信息")
    @ApiImplicitParam(value = "查询条件", name = "queryMap",required = false,dataType = "Map", paramType = "body")
    public ResponseData<User> userQueryByNameAndStatus(@RequestBody Map<String,Object> queryMap){
        List<User> users = userService.userByNameAndStatus(queryMap);
        if (users != null){
            return ResponseData.success().putDataVule("userList",users);
        }else{
            return ResponseData.serverInternalError();
        }
    }

    //    分页查询用户信息
    @PostMapping("/userPage")
    @ApiOperation(value = "分页查询", notes = "分页查询所有用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name="pageNum", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = false, dataType = "Integer", paramType = "query")
    })
    public ResponseData<User> userPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        IPage<User> userIPage = userService.userPage(pageNum,pageSize);
        return ResponseData.success().putDataVule("total",userIPage.getTotal())
                .putDataVule("page",userIPage.getPages())
                .putDataVule("userList",userIPage.getRecords());
    }

    //    分页条件查询用户信息
    @PostMapping("/userPageByCondition")
    @ApiOperation(value = "条件分页查询", notes = "根据用户名和状态信息条件查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name = "pageNum", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = false, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "查询条件", name = "queryMap", required = false, dataType = "Map", paramType = "body")
    })
    public ResponseData<User> userPageByCondition(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestBody Map<String,Object> queryMap){
        IPage userIPage = userService.userPageByCondition(pageNum,pageSize,queryMap);
        return ResponseData.success().putDataVule("total",userIPage.getTotal())
                .putDataVule("page",userIPage.getPages())
                .putDataVule("userlist",userIPage.getRecords());
    }

    //    更新用户状态信息
    @PutMapping("/updateStatus/{id}")
    @ApiOperation(value = "更新用户状态信息", notes = "管理员对用户实行解封和封号处理")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户状态", name = "status", required = true, dataType = "Integer", paramType = "body"),
//            @ApiImplicitParam(value = "用户Id",name = "id",required = true,dataType = "Integer",paramType = "body")
    })

    public ResponseData<User> userStatusUpdate(@RequestBody Integer status, @PathVariable Integer id){
        User user = new User(id,status);
        Integer integer = userService.updateUserStatus(user);
        if (integer > 0 ){
            return  ResponseData.success();
        }else{
            return ResponseData.cusstomerError("更新失败");
        }
    }

}
