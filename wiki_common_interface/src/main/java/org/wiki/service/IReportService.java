package org.wiki.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wiki.pojo.Wiki_Animation;
import org.wiki.pojo.Wiki_Report;

import java.util.List;

public interface IReportService {
    //查询未处理的举报信息
    public List<Wiki_Report> getUntreated();
    //分页查询未处理的举报消息
    public IPage<Wiki_Report> getUntreatedPage(Integer pageNum,Integer pageSize);
    //添加一条举报记录
    public Integer reportAnimation(Wiki_Report report);
}
