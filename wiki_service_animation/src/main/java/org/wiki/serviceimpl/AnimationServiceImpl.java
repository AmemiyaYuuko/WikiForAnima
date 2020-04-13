package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.wiki.mapper.AnimationMapper;
import org.wiki.pojo.AnimationHistory;
import org.wiki.pojo.Wiki_Animation;
import org.wiki.service.IAnimationService;
import org.wiki.service.IHistoryService;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AnimationServiceImpl implements IAnimationService {
    @Autowired
    AnimationMapper animationMapper;
    @Reference
    IHistoryService historyService;
    /**
     * 获取所有动漫信息的列表
     *
     * @return
     */
    @Override
    public List<Wiki_Animation> getAnimList() {
        return animationMapper.selectList(null);
    }

    @Override
    public Wiki_Animation getAnimById(Integer id) {
        return animationMapper.selectById(id);
    }

    @Override
    public List<Wiki_Animation> getAnimByNameAndLabel(Map<String, Object> queryMap) {
        return animationMapper.selectList(nameAndLabelWrapper(queryMap));
    }

    @Override
    public List<Wiki_Animation> getAnimBySeason(Date date) {
        return animationMapper.selectList(seasonWrapper(date));
    }

    @Override
    public List<Wiki_Animation> fullTextSearch(String str) {
        return animationMapper.selectList(fullTextWrapper(str));
    }

    @Override
    public IPage<Wiki_Animation> getAnimListPage(Integer pageNum, Integer pageSize) {
        IPage<Wiki_Animation> page=new Page<>(pageNum,pageSize);
        page=animationMapper.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<Wiki_Animation> getAnimByNameAndLabelPage(Integer pageNum, Integer pageSize, Map<String, Object> queryMap) {
        IPage<Wiki_Animation> page=new Page<>(pageNum,pageSize);
        page=animationMapper.selectPage(page,nameAndLabelWrapper(queryMap));
        return page;
    }

    @Override
    public IPage<Wiki_Animation> getAnimBySeasonPage(Integer pageNum, Integer pageSize, Date date) {
        IPage<Wiki_Animation> page=new Page<>(pageNum,pageSize);
        page=animationMapper.selectPage(page,seasonWrapper(date));
        return page;
    }

    @Override
    public IPage<Wiki_Animation> fullTextSearchPage(Integer pageNum, Integer pageSize, String str) {
        IPage<Wiki_Animation> page=new Page<>(pageNum,pageSize);
        page=animationMapper.selectPage(page,fullTextWrapper(str));
        return page;
    }

    @Override
    public Integer addAnimationInfo(Wiki_Animation animation) {

        return animationMapper.insert(animation);
    }

    @Override
    public Integer modifyAnimationInfo(Wiki_Animation animation,Integer userId) {
        AnimationHistory history=new AnimationHistory();
        history.setAnimationId(animation.getAnimationId());
        history.setAnimationImg(animation.getAnimationImg());
        history.setAnimationIntroduce(animation.getAnimationIntroduce());
        history.setAnimationLabel(animation.getAnimationLabel());
        history.setAnimationLink(animation.getAnimationLink());
        history.setAnimationName(animation.getAnimationName());
        history.setAnimationStaff(animation.getAnimationStaff());
        history.setAnimationStatus(animation.getAnimationStatus());
        history.setAnimationTime(animation.getAnimationTime());
        history.setAnimationVoiceactor(animation.getAnimationVoiceactor());
        history.setChangeTime(new Date());
        history.setUserId(userId);
        Integer success=0;
        success=historyService.insertAnimationHistory(history);
        if(success==1){
            success=animationMapper.updateById(animation);
        }
        return success;
    }

    @Override
    public Integer deleteAnimation(Integer id) {
        return animationMapper.deleteById(id);
    }

    @Override
    public Integer modifyAnimationStatus(Integer id, Integer status) {
        Wiki_Animation animation=getAnimById(id);
        animation.setAnimationStatus(status);
        return animationMapper.updateById(animation);
    }

    /**
     * 根据标签和动漫名查询数据的参数处理方法
     *
     * @param queryMap
     * @return
     */
    public QueryWrapper<Wiki_Animation> nameAndLabelWrapper(Map<String, Object> queryMap) {
        QueryWrapper<Wiki_Animation> animationWrapper = null;
        if (queryMap != null) {
            animationWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(queryMap.get("animation_name"))) {
                animationWrapper.like("animation_name", queryMap.get("animation_name"));
            }
            if (!StringUtils.isEmpty(queryMap.get("animation_label"))) {
                animationWrapper.like("animation_label", queryMap.get("animation_label"));
            }
        }
        return animationWrapper;
    }

    /**
     * 根据传递的日期返回这一季度的所有动漫信息的参数处理方法
     *
     * @param date
     * @return
     */
    public QueryWrapper<Wiki_Animation> seasonWrapper(Date date) {
        QueryWrapper<Wiki_Animation> animationWrapper = null;
        if (date != null) {
            animationWrapper = new QueryWrapper<>();
            //将传入的时间的月份提取出来
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] dateTime = simpleDateFormat.format(date).toString().split("-");
            Integer year = Integer.parseInt(dateTime[0]);
            Integer month = Integer.parseInt(dateTime[1]);
            if (month >= 1 && month < 4) {
                animationWrapper.ge("animation_time", year + "-" + "01-01")
                        .lt("animation_time", year + "-" + "04-01");
            }
            if (month >= 4 && month < 7) {
                animationWrapper.ge("animation_time", year + "-" + "04-01")
                        .lt("animation_time", year + "-" + "07-01");
            }
            if (month >= 7 && month < 10) {
                animationWrapper.ge("animation_time", year + "-" + "07-01")
                        .lt("animation_time", year + "-" + "10-01");
            }
            if (month >= 10) {
                animationWrapper.ge("animation_time", year + "-" + "10-01");
            }
        }
        return animationWrapper;
    }

    /**
     * 全文搜索的参数组合
     * @param str
     * @return
     */
    public QueryWrapper<Wiki_Animation> fullTextWrapper(String str) {
        QueryWrapper<Wiki_Animation> animationWrapper = null;
        if (str!= null) {
            animationWrapper = new QueryWrapper<>();
            animationWrapper.like("animation_name", str)
                    .or().like("animation_label", str)
                    .or().like("animation_introduce", str)
                    .or().like("animation_voiceactor", str)
                    .or().like("animation_staff", str);
        }
        return animationWrapper;
    }
}
