package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.wiki.mapper.AdminMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.wiki.pojo.Admin;
import org.wiki.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin selectByName(String manager_name) {
       return adminMapper.selectOne(new QueryWrapper<Admin>().eq("manager_name",manager_name));
    }
}
