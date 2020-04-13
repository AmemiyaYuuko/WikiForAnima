package org.wiki.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wiki.pojo.Wiki_Animation;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAnimationService {
   //查询动漫信息列表
   public List<Wiki_Animation> getAnimList();
   //根据id查询动漫
   public Wiki_Animation getAnimById(Integer id);
   //根据动漫名以及标签查询动漫信息
   public List<Wiki_Animation> getAnimByNameAndLabel(Map<String,Object> queryMap);
   //根据季度获取动漫信息列表
   public List<Wiki_Animation> getAnimBySeason(Date date);
   //全文检索动漫信息
   public List<Wiki_Animation> fullTextSearch(String str);
   //分页查询所有信息
   public IPage<Wiki_Animation> getAnimListPage(Integer pageNum,Integer pageSize);
   //分页根据动漫名以及标签查询动漫信息
   public IPage<Wiki_Animation> getAnimByNameAndLabelPage(Integer pageNum,Integer pageSize,Map<String,Object> queryMap);
   //分页季度查询动漫信息列表
   public IPage<Wiki_Animation> getAnimBySeasonPage(Integer pageNum,Integer pageSize,Date date);
   //分页全文检索动漫信息
   public IPage<Wiki_Animation> fullTextSearchPage(Integer pageNum,Integer pageSize,String str);

   //增加一条动漫信息
   public Integer addAnimationInfo(Wiki_Animation animation);
   //修改动漫信息
   public Integer modifyAnimationInfo(Wiki_Animation animation,Integer userId);
   //根据id删除动漫信息
   public Integer deleteAnimation(Integer id);
   //根据id修改动漫信息的状态
   public Integer modifyAnimationStatus(Integer id,Integer status);
}
