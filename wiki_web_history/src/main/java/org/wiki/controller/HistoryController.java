package org.wiki.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.wiki.entity.ResponseData;
import org.wiki.pojo.AnimationHistory;
import org.wiki.service.IHistoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
@ResponseBody
@Api(tags = "历史版本管理")
public class HistoryController {
    @Reference
    private IHistoryService iHistoryService;

    @GetMapping("/selectbyid")
    @ApiOperation(value = "根据id获取历史记录", notes = "获取列表")
    @ApiImplicitParam(value = "动漫id",name = "id",required = true,dataType = "Integer",paramType = "query")
    public ResponseData<AnimationHistory> historyList(Integer id){
        List<AnimationHistory> historylist=iHistoryService.selectHistoryById(id);
        if(historylist != null){
            return ResponseData.success().putDataVule("historylist",historylist);
        }else {
            return ResponseData.serverInternalError();
        }
    }

    @PostMapping("/selectbyname")
    @ApiOperation(value = "根据名称获取历史记录,", notes = "获取列表")
    @ApiImplicitParam(value = "动漫名称",name = "name",required = true,paramType = "query")
    public ResponseData<AnimationHistory> historyByname(@RequestParam String name){
        List<AnimationHistory> historylist=iHistoryService.selectHistoryByName(name);
        if(historylist != null){
            return ResponseData.success().putDataVule("historylist",historylist);
        }else {
            return ResponseData.serverInternalError();
        }
    }

    @GetMapping("/selectbyidpage")
    @ApiOperation(value = "根据id分页获取历史记录,", notes = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数",name = "pagenum",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "每页条数",name = "pagesize",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "动漫id",name = "id",required = false,dataType = "Integer",paramType = "query")
    })
    public ResponseData<AnimationHistory> historyByIdPage(@RequestParam Integer pagenum, @RequestParam Integer pagesize,@RequestParam Integer id){
        IPage<AnimationHistory> historypage=iHistoryService.selectHistoryByIdPage(pagenum,pagesize,id);
        return ResponseData.success().putDataVule("totla",historypage.getTotal())
                .putDataVule("pages",historypage.getPages())
                .putDataVule("historylist",historypage.getRecords());

    }

    @PostMapping("/selectbynamepage")
    @ApiOperation(value = "根据名称分页获取历史记录,", notes = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数",name = "pagenum",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "每页条数",name = "pagesize",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "动漫id",name = "name",required = false,dataType = "String",paramType = "query")
    })
    public ResponseData<AnimationHistory> historyByNamePage(@RequestParam Integer pagenum, @RequestParam Integer pagesize,@RequestParam String name){
        IPage<AnimationHistory> historypage=iHistoryService.selectHistoryByNamePage(pagenum,pagesize,name);
        return ResponseData.success().putDataVule("totla",historypage.getTotal())
                .putDataVule("pages",historypage.getPages())
                .putDataVule("historylist",historypage.getRecords());

    }

    @GetMapping("/rollbacknew")
    @ApiOperation(value = "回滚最新", notes = "回滚")
    @ApiImplicitParam(value = "动漫id",name = "id",required = true,dataType = "Integer",paramType = "query")
    public void rollbackNew(Integer id){
        iHistoryService.rollbackToLast(id);
    }

    @PostMapping("/rollbackchoose")
    @ApiOperation(value = "回滚指定", notes = "回滚")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "动漫id",name = "id",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "历史版本id",name = "history_id",required = false,dataType = "Integer",paramType = "query")
    })
    public void rollbackChoose(Integer id,Integer history_id){
        iHistoryService.rollbackToChooseVersion(id,history_id);
    }

    @PostMapping("/maxchange")
    @ApiOperation(value = "修改统计", notes = "查询修改次数")
    @ApiImplicitParam(value = "控制升序降序，true为降序，false为升序",name = "a")
    public ResponseData<Map<String, Object>> getmax(boolean a){
        List<Map<String, Object>> list=iHistoryService.findMaxChange(a);
        if(list != null){
            return ResponseData.success().putDataVule("list",list);
        }else {
            return ResponseData.serverInternalError();
        }
    }




}
