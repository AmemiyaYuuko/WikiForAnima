package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.wiki.mapper.ReportMapper;
import org.wiki.pojo.Wiki_Report;
import org.wiki.service.IAnimationService;
import org.wiki.service.IReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private IAnimationService animationService;
    @Override
    public List<Wiki_Report> getUntreated() {
        return reportMapper.selectList(new QueryWrapper<Wiki_Report>().eq("status",0));
    }

    @Override
    public IPage<Wiki_Report> getUntreatedPage(Integer pageNum, Integer pageSize) {
        IPage<Wiki_Report> page=new Page<>(pageNum,pageSize);
        page=reportMapper.selectPage(page,new QueryWrapper<Wiki_Report>().eq("status",0));
        return page;
    }
    @Override
    public Integer reportAnimation(Wiki_Report report) {
        Integer modifySuccess=animationService.modifyAnimationStatus(report.getAnimationId(),1);
        Integer success=0;
        if(modifySuccess==1){
            success=reportMapper.insert(report);
        }
        return success;
    }
}
