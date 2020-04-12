package org.wiki.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wiki.history.AnimationHistory;

import java.util.List;
import java.util.Map;

//历史版本回滚接口
public interface IHistoryService {
    //查看历史版本,根据动漫id查看对应的历史版本
    public List<AnimationHistory> selectHistoryById(Integer id);

    //查看历史版本，根据动漫名称查
    public List<AnimationHistory> selectHistoryByName(String name);

    //根据动漫id查看，分页
    public IPage<AnimationHistory> selectHistoryByIdPage(Integer pagenum, Integer pagesize,Integer id);

    //根据名称查看，分页
    public IPage<AnimationHistory> selectHistoryByNamePage(Integer pagenum, Integer pagesize,String name);

    //根据动漫id回滚到上一个版本
    public void rollbackToLast(Integer id);

    //根据动漫id回滚到指定版本
    public void rollbackToChooseVersion(Integer id,Integer history_id);

    //插入历史表



    //获取动漫的修改次数，可以作为一个动漫的热度使用,boolean true表示降序，其他情况表示升序
    public List<Map<String, Object>> findMaxChange(boolean a);

    //统计所有动漫的修改次数，分页
    //public IPage<AnimationHistory> findAllChangeNum(Integer pagenum, Integer pagesize);


}
