package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.wiki.mapper.AnimationHistoryMapper;
import org.wiki.pojo.AnimationHistory;
import org.wiki.service.IHistoryService;

import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private AnimationHistoryMapper historymapper;

    @Override
    public List<AnimationHistory> selectHistoryById(Integer id) {
        return historymapper.selectList(new QueryWrapper<AnimationHistory>().eq("animation_id", id));

    }

    @Override
    public List<AnimationHistory> selectHistoryByName(String name) {
        return historymapper.selectList(new QueryWrapper<AnimationHistory>().like("animation_name", name));
    }

    @Override
    public IPage<AnimationHistory> selectHistoryByIdPage(Integer pagenum, Integer pagesize, Integer id) {
        IPage<AnimationHistory> page = new Page<>(pagenum, pagesize);
        page = historymapper.selectPage(page, new QueryWrapper<AnimationHistory>().eq("animation_id", id));
        return page;
    }

    @Override
    public IPage<AnimationHistory> selectHistoryByNamePage(Integer pagenum, Integer pagesize, String name) {
        IPage<AnimationHistory> page = new Page<>(pagenum, pagesize);
        page = historymapper.selectPage(page, new QueryWrapper<AnimationHistory>().like("animation_name", name));
        return page;
    }

    @Override
    public void rollbackToLast(Integer id) {
        List<AnimationHistory> historylist = historymapper.selectList(new QueryWrapper<AnimationHistory>().eq("animation_id", id).orderByDesc("change_time"));
        AnimationHistory historytmp=historylist.get(0);
        historytmp.setAnimationStatus(1);
        //因为数据库设置了触发器，只要对status进行更新操作，数据库的触发器就会自动触发，将此版本的数据写入动漫表
        historymapper.updateById(historytmp);

    }

    @Override
    public void rollbackToChooseVersion(Integer id, Integer history_id) {
        //同样利用触发器进行回滚操作
        AnimationHistory historytmp=historymapper.selectOne(new QueryWrapper<AnimationHistory>().eq("animation_id",id).eq("history_id",history_id));
        historytmp.setAnimationStatus(1);
        historymapper.updateById(historytmp);

    }

    @Override
    public Integer insertAnimationHistory(AnimationHistory animationHistory) {
        return historymapper.insert(animationHistory);
    }

    @Override
    public List<Map<String, Object>> findMaxChange(boolean a) {
        List<Map<String, Object>> list=null;
        if(a==true) {
            list = historymapper.selectMaps(new QueryWrapper<AnimationHistory>().select("animation_name", "count(*)", "animation_id").groupBy("animation_name").groupBy("animation_id").orderByDesc("count(*)"));
        }else{
            list = historymapper.selectMaps(new QueryWrapper<AnimationHistory>().select("animation_name", "count(*)", "animation_id").groupBy("animation_name").groupBy("animation_id").orderByAsc("count(*)"));
        }
        return list;
    }


}
