package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wiki.pojo.Wiki_Animation;
import org.wiki.pojo.Wiki_Report;
import org.wiki.service.IAnimationService;
import org.wiki.service.IReportService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portal")
public class TAnimationController {
    @Reference
    private IAnimationService animationService;
    @Reference
    private IReportService reportService;

    /**
     * 获取动漫信息列表
     * @param model
     * @return
     */
    @RequestMapping("/animList")
    public String getAnimList(Model model){
        List<Wiki_Animation> animationList=animationService.getAnimList();
        model.addAttribute("animList",animationList);
        return "test";
    }

    /**
     * 分页检索动漫信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/animPage")
    public ModelAndView getAnimPage(Integer pageNum,Integer pageSize){
        IPage<Wiki_Animation> animationIPage=animationService.getAnimListPage(pageNum,pageSize);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pages",animationIPage.getPages());
        modelAndView.addObject("animationPage",animationIPage);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 根据ID查找动漫信息
     * @param id
     * @return
     */
    @RequestMapping("/selectAnim")
    public ModelAndView getAnimByID(Integer id){
        Wiki_Animation animation=animationService.getAnimById(id);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("animation",animation);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 根据动漫名和标签检索动漫信息
     * @param name
     * @param label
     * @return
     */
    @RequestMapping("/selectByNL")
    public ModelAndView getAnimByNL(String name,String label){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("animation_name",name);
        queryMap.put("animation_label",label);
        List<Wiki_Animation> animationList=animationService.getAnimByNameAndLabel(queryMap);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("animList",animationList);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 根据动漫名和标签分页检索动漫信息
     * @param pageName
     * @param pageSize
     * @param name
     * @param label
     * @return
     */
    @RequestMapping("/selectPageByNL")
    public ModelAndView getPageByNL(Integer pageName,Integer pageSize,String name,String label){
        Map<String,Object> queryMap=new HashMap<>();
        queryMap.put("animation_name",name);
        queryMap.put("animation_label",label);
        IPage<Wiki_Animation> animationPage=animationService.getAnimByNameAndLabelPage(pageName,pageSize,queryMap);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pages",animationPage.getPages());
        modelAndView.addObject("animationPage",animationPage);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 季度检索动漫信息
     * @param date
     * @return
     */
    @RequestMapping("/selectAnimBySeason")
    public ModelAndView getAnimBySeason(Date date){
        List<Wiki_Animation> animationList=animationService.getAnimBySeason(date);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("animationList",animationList);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 季度分页检索动漫信息
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @RequestMapping("/selectPageBySeason")
    public ModelAndView getPageBySeason(Integer pageNum,Integer pageSize,Date date){
        IPage<Wiki_Animation> animationIPage=animationService.getAnimBySeasonPage(pageNum,pageSize,date);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pages",animationIPage.getPages());
        modelAndView.addObject("animationPage",animationIPage);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 全文检索动漫信息
     * @param str
     * @return
     */
    @RequestMapping("/fullText")
    public ModelAndView fullPageSelect(String str){
        List<Wiki_Animation> animationList=animationService.fullTextSearch(str);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("animList",animationList);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     *全文分页检索动漫信息
     * @param pageNum
     * @param pageSize
     * @param str
     * @return
     */
    @RequestMapping("/fUllTextPage")
    public ModelAndView fullTextPage(Integer pageNum,Integer pageSize,String str){
        IPage<Wiki_Animation> animationIPage=animationService.fullTextSearchPage(pageNum,pageSize,str);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pages",animationIPage.getPages());
        modelAndView.addObject("animationPage",animationIPage);
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 添加动漫信息
     * @param animation
     * @return
     */
    @RequestMapping("/addAnimation")
    public String addAnimation(Wiki_Animation animation){
        animationService.addAnimationInfo(animation);
        return "";
    }

    /**
     * 修改动漫信息
     * @param animation
     * @return
     */
    @RequestMapping("/modifyAnimationInfo")
    public String modifyAnimationInfo(Wiki_Animation animation,Integer userId){
        animationService.modifyAnimationInfo(animation,userId);
        return "";
    }

    /**
     * 删除动漫
     * @param id
     * @return
     */
    @RequestMapping("/deleteAnimation")
    public String deleteAnimation(Integer id){
        animationService.deleteAnimation(id);
        return "";
    }

    /**
     * 举报动漫
     * @param report
     * @return
     */
    @RequestMapping("/reportAnimation")
    public String reportAnimation(Wiki_Report report){
        reportService.reportAnimation(report);
        return "";
    }
}
