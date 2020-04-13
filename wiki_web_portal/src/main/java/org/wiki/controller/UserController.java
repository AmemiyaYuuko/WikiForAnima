package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wiki.pojo.User;
import org.wiki.pojo.UserCollection;
import org.wiki.service.IUserService;

import java.util.List;

@Controller
@RequestMapping("/wiki/user")
@Api(tags = "用户信息接口")
public class UserController {
    @Reference
    private IUserService userService;

    private String message;
//
////    根据用户名查询用户信息 用于用户登录
//    @PostMapping("/login")
//    @ApiOperation(value = "用户登录接口",notes = "根据用户名查询用户")
//    @ApiImplicitParam(value = "用户名", name = "name", required = true, dataType = "String", paramType = "query")
//    public ResponseData<User> userLogin(@RequestParam String name, @RequestParam String password){
//        User user = userService.selectOne(name);
//        if (user != null){
//           boolean checkPwd = password.equals(user.getPassword());
//           if (checkPwd){
//               return ResponseData.success();
//           }else {
//               return ResponseData.cusstomerError("你的密码不正确");
//           }
//        }else{
//            return ResponseData.cusstomerError("该用户不存在");
//        }
//    }
//
////    用户注册信息
//    @PostMapping("/register")
//    @ApiOperation(value = "用户注册", notes = "用户根据用户名和密码注册信息(用户名唯一)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(value = "用户名", name = "name",required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(value = "登录密码", name="password", required = true,dataType = "String", paramType = "query")
//    })
//    public ResponseData<User> userReg(String name, String password){
//        User user = new User(name, password);
//       Integer integer =  userService.register(user);
//       if (integer > 0){
//           return ResponseData.success();
//       }else {
//           return ResponseData.cusstomerError("注册失败");
//       }
//    }
//
////    更新用户信息
//    @PutMapping("/update")
//    @ApiOperation(value = "用户更新信息", notes = "用户更新信息，用户名唯一，用户名和密码不能为空。")
//    @ApiImplicitParam(value = "用户信息", name = "user", required = true, dataType = "User", paramType = "body")
//    public ResponseData<User> userUpdate(@RequestBody User user){
//        Integer integer =  userService.updateUser(user);
//        if (integer > 0 ){
//            return  ResponseData.success();
//        }else{
//            return ResponseData.cusstomerError("更新失败");
//        }
//    }
//
//
////    用户查询自己的收藏信息
//    @GetMapping("/selectCollection/{user_id}")
//    @ApiOperation(value = "用户查看自己的收藏", notes = "用户根据自己的用户Id查看自己的收藏")
//    public ResponseData<UserCollection> findCollections(@PathVariable Integer user_id){
//        List<UserCollection> collections = userService.queryCollection(user_id);
//        if (collections.size() > 0){
//            return ResponseData.success().putDataVule("collectionList",collections);
//        }else{
//            return ResponseData.cusstomerError("你还没有收藏动漫");
//        }
//    }

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    //    根据用户名查询用户信息 用于用户登录
    @PostMapping("/login")
    public String userLogin(String name,String password, Model model){
        User user = userService.selectOne(name);
        if (user != null){
            boolean checkPwd = password.equals(user.getPassword());
            if (checkPwd){
                model.addAttribute("user",user);
                return "PersonalNews";
            }else {
                String message = "您的密码不正确请重新输入";
                model.addAttribute("msg",message);
                return "login";
            }
        }else{
            String message = "该用户不存在";
            model.addAttribute("msg",message);
            return "login";
        }
    }

    //    用户注册信息
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户根据用户名和密码注册信息(用户名唯一)")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "name",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "登录密码", name="password", required = true,dataType = "String", paramType = "query")
    })
    public String  userReg(String name, String password,Model model){
        User user = userService.selectOne(name);
        if (user == null){
            user = new User(name,password);
            model.addAttribute("user",user);
            return "PersonalNews";
        }else {
           message = "该用户已存在,注册失败";
            model.addAttribute("msg",message);
            return "login";
        }
    }

    //    更新用户信息
    @PostMapping("/update/{id}")
    @ApiOperation(value = "用户更新信息", notes = "用户更新信息，用户名唯一，用户名和密码不能为空。")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户ID", name = "id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "用户名", name = "name", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(value = "用户密码", name = "password", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(value = "用户年龄", name = "age", required = false, dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(value = "用户性别", name = "sex", required = false, dataType = "String", paramType = "body"),
            @ApiImplicitParam(value = "用户邮箱", name = "email", required = false, dataType = "String", paramType = "body"),
            @ApiImplicitParam(value = "用户状态", name = "status", required = true, dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(value = "用户图像", name = "name", required = true, dataType = "String", paramType = "body")
    })
    public String userUpdate(@PathVariable Integer id, String name, String password, Integer age, String sex, String email, Integer status, String img,Model model){
        User user = new User(id,name,password,age,sex,email,status,img);
        Integer integer =  userService.updateUser(user);
        if (integer > 0 ){
            message = "更新成功";
            model.addAttribute("msg",message);
            return  "login";
        }else{
            message = "更新失败";
            model.addAttribute("msg",message);
            return "PersonalNews";
        }
    }

    //    用户查询自己的收藏信息
    @GetMapping("/selectCollection/{user_id}")
    @ApiOperation(value = "用户查看自己的收藏", notes = "用户根据自己的用户Id查看自己的收藏")
    public String findCollections(@PathVariable("user_id") Integer user_id,Model model){
        System.out.println(user_id);
        List<UserCollection> collections = userService.queryCollection(user_id);
        model.addAttribute("collections",collections);
        return "collection";// 返回收藏页面
    }

    //    增加用户收藏
    @PostMapping("/insertCollection")
    public String increaseCollection(Integer animationId, Integer userId, String animationName, String animationImg,Model model){
        UserCollection collection = new UserCollection(animationId, userId, animationName, animationImg);
        Integer integer = userService.insertCollection(collection);
        if (integer > 0){
            message = "收藏成功";
            model.addAttribute("msg", message);
        }else {
            message = "收藏失败";
            model.addAttribute("msg", message);
        }
        return "index";
    }
//    用户删除收藏
    @GetMapping("/deleteCollection/{user_id}/{animation_id}")
    public String deleteCollection(@PathVariable Integer user_id,@PathVariable Integer animation_id,Model model){
        userService.deleteCollection(user_id,animation_id);
        return "redirect:/wiki/user/selectCollection/{user_id}";
    }
}
