package org.wiki.service;


import org.wiki.pojo.Admin;

public interface IAdminService {
//    按用户名查询管理员
    public Admin selectByName(String manager_ame);
}
