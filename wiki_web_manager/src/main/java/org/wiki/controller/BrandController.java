package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wiki.entity.ResponseData;
import org.wiki.entity.Brand;
import org.wiki.service.IBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
@ResponseBody
@Api(tags = "商品品牌管理")
public class BrandController {
    @Reference
    private IBrandService iBrandService;
    @GetMapping("/brandlist")
    @ApiOperation(value = "品牌列表", notes = "获取列表")
    public ResponseData<Brand> brandlist(){
        List<Brand> brandList = iBrandService.brandList();
        if(brandList != null){
            return ResponseData.success().putDataVule("brandlist",brandList);
        }else {
            return ResponseData.serverInternalError();
        }
    }
    @PostMapping("/brandQuery")
    @ApiOperation(value = "条件查询品牌列表",notes = "根据条件查询品牌列表信息")
    @ApiImplicitParam(value = "查询条件" ,name="queryMap" ,required = false,dataType = "Map",paramType = "body")
    public ResponseData<Brand> brandListQuery(@RequestBody Map<String ,Object> queryMap){
        List<Brand> brandList = iBrandService.brandListByCondition(queryMap);
        if(brandList!= null){
            return  ResponseData.success().putDataVule("brandList",brandList);
        }else{
            return ResponseData.serverInternalError();
        }
    }
    @GetMapping("/brandPage")
    @ApiOperation(value = "分页查询",notes = "分页查询所有品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数",name = "pagenum",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "每页条数",name = "pagesize",required = false,dataType = "Integer",paramType = "query")
    })
    public ResponseData<Brand> bradPage(@RequestParam  Integer pagenum,@RequestParam Integer pagesize){
        IPage<Brand> brandPage =  iBrandService.brandPage(pagenum,pagesize);
        return ResponseData.success().putDataVule("totla",brandPage.getTotal())
                .putDataVule("pages",brandPage.getPages())
                .putDataVule("brandList",brandPage.getRecords());

    }
    @PostMapping("/brandPage")
    @ApiOperation(value = "分页条件查询",notes = "根据条件分页查询品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数",name = "pagenum",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "每页条数",name = "pagesize",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "查询条件",name = "queryMap",required = false,dataType = "Map",paramType = "body")
    })
    public  ResponseData<Brand> brandPageQuery(@RequestParam  Integer pagenum,@RequestParam Integer pagesize,@RequestBody Map<String,Object> queryMap){
        IPage<Brand> brandPage =  iBrandService.brandPageByCondition(pagenum,pagesize,queryMap);
        return ResponseData.success().putDataVule("totla",brandPage.getTotal())
                .putDataVule("pages",brandPage.getPages())
                .putDataVule("brandList",brandPage.getRecords());
    }
}
