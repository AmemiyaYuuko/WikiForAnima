package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wiki.mapper.CollectionMapper;
import org.wiki.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.wiki.pojo.User;
import org.wiki.pojo.UserCollection;
import org.wiki.service.IUserService;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Override
    public User selectOne(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_name",name));

    }

    @Override
    public List<User> userList() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> userByNameAndStatus(Map<String, Object> queryMap) {
        return userMapper.selectList(queryWrapper(queryMap));
    }

    @Override
    public IPage<User> userPage(Integer pageNum, Integer pageSize) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        page = userMapper.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<User> userPageByCondition(Integer pageNum, Integer pageSize, Map<String, Object> queryMap) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        page = userMapper.selectPage(page,queryWrapper(queryMap));
        return page;
    }

    @Override
    public Integer register(User user) {
         return userMapper.insert(user);
    }

    @Override
    public Integer updateUser(User user) {
       return userMapper.updateById(user);
    }

    @Override
    public Integer updateUserStatus(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public List<UserCollection> queryCollection(Integer user_id) {
        return  collectionMapper.selectList(new QueryWrapper<UserCollection>().eq("user_id",user_id));
    }

    @Override
    public Integer insertCollection(UserCollection collection) {
        return collectionMapper.insert(collection);
    }

    @Override
    public Integer deleteCollection(Integer userId, Integer animationId) {
        return collectionMapper.delete(new QueryWrapper<UserCollection>().eq("user_id",userId)
                                                                    .eq("animation_id",animationId));
    }

    private QueryWrapper<User> queryWrapper(Map<String, Object> queryMap){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(queryMap != null){
            if (!StringUtils.isEmpty(queryMap.get("name"))){
                queryWrapper.like("user_name",queryMap.get("name"));
            }
            if (!StringUtils.isEmpty(queryMap.get("status"))){
                queryWrapper.eq("user_status",queryMap.get("status"));
            }
        }
        return queryWrapper;
    }
}
