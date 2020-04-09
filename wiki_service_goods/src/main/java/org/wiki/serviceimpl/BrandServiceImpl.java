package org.wiki.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wiki.mapper.BrandMapper;
import org.wiki.entity.Brand;
import org.wiki.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> brandList() {
        return brandMapper.selectList(null);
    }

    @Override
    public List<Brand> brandListByCondition(Map<String, Object> querMap) {
        return brandMapper.selectList(queryWrapper(querMap)) ;
    }

    @Override
    public IPage<Brand> brandPage(Integer pagenum, Integer pagesize) {
        IPage<Brand> page = new Page<>(pagenum,pagesize);
        page = brandMapper.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<Brand> brandPageByCondition(Integer pagenum, Integer pagesize, Map<String, Object> queryMap) {
        IPage<Brand> page = new Page<>(pagenum,pagesize);
        page = brandMapper.selectPage(page,queryWrapper(queryMap));
        return page;
    }

    @Override
    public Brand brandById(Integer id) {
        return null;
    }

    @Override
    public Integer saveBrand(Brand brand) {
        return null;
    }

    @Override
    public Integer modifyBrand(Brand brand) {
        return null;
    }

    @Override
    public Integer removeBrand(Integer id) {
        return null;
    }
    private QueryWrapper<Brand> queryWrapper(Map<String,Object> queryMap){
        // 查询条件
        QueryWrapper<Brand> queryWrapper = null;
        if(queryMap !=null){
            queryWrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(queryMap.get("name"))){
                queryWrapper.like("name",queryMap.get("name"));
            }
            if(!StringUtils.isEmpty(queryMap.get("letter"))){
                queryWrapper.eq("letter",queryMap.get("letter"));
            }
        }
        return  queryWrapper;
    }

}
