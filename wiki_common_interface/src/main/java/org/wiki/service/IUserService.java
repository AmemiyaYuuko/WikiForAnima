package org.wiki.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wiki.pojo.User;
import org.wiki.pojo.UserCollection;

import java.util.List;
import java.util.Map;

public interface IUserService {
//    根据用户姓名查询用户信息
    public User selectOne(String name);
//    查询用户列表
    public List<User> userList();
//  根据用户名和状态信息查询用户
    public List<User> userByNameAndStatus(Map<String, Object> queryMap);
//    分页查询用户信息
    public IPage<User> userPage(Integer pageNum, Integer pageSize);
//    分页条件查询用户信息
    public IPage<User> userPageByCondition(Integer pageNum, Integer pageSize, Map<String, Object> queryMap);
    //  注册用户信息
    public Integer register(User user);
//    更新用户信息
    public Integer updateUser(User user);
//    修改用户状态信息
    public Integer updateUserStatus(User user);
//    用户根据自己的用户id查看自己的收藏
    public List<UserCollection> queryCollection(Integer user_id);
//    增加收藏
    public Integer insertCollection(UserCollection collection);
//    根据用户Id和动漫Id删除收藏
    public Integer deleteCollection(Integer userId, Integer animationId);
}
