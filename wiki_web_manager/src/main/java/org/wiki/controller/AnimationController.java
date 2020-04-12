package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.wiki.entity.ResponseData;
import org.wiki.pojo.Wiki_Animation;
import org.wiki.service.IAnimationService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animation")
@ResponseBody
@Api(tags = "动漫维基百科")
public class AnimationController {
    @Reference
    private IAnimationService animationService;

    /**
     * 查询所有的动漫信息
     *
     * @return
     */
    @GetMapping("/animList")
    @ApiOperation(value = "动漫列表", notes = "获取列表")
    public ResponseData<Wiki_Animation> animList() {
        List<Wiki_Animation> animList = animationService.getAnimList();
        if (animList != null) {
            return ResponseData.success().putDataVule("animList", animList);
        } else {
            return ResponseData.serverInternalError();
        }
    }

    /**
     * 根据id查询动漫信息
     *
     * @param id
     * @return
     */
    @GetMapping("/animation")
    @ApiOperation(value = "ID获取动漫对象", notes = "获取动漫对象")
    @ApiImplicitParam(value = "查询条件", name = "id", required = true, dataType = "Integer", paramType = "query")
    public ResponseData<Wiki_Animation> getAnimById(@RequestParam Integer id) {
        Wiki_Animation animation = animationService.getAnimById(id);
        return ResponseData.success().putDataVule("Animation", animation);
    }

    /**
     * 根据标签和动漫名多条件查询动漫信息
     *
     * @param queryMap
     * @return
     */
    @PostMapping("/nameLabelSearch")
    @ApiOperation(value = "标签及动漫名查询动漫列表", notes = "根据动漫名称和标签查询动漫列表信息")
    @ApiImplicitParam(value = "查询条件", name = "queryMap", required = false, dataType = "Map", paramType = "body")
    public ResponseData<Wiki_Animation> animListNameLabelQuery(@RequestBody Map<String, Object> queryMap) {
        List<Wiki_Animation> animList = animationService.getAnimByNameAndLabel(queryMap);
        if (animList != null) {
            return ResponseData.success().putDataVule("animList", animList);
        } else {
            return ResponseData.serverInternalError();
        }
    }

    /**
     * 根据季度多条件查询动漫信息
     *
     * @param date
     * @return
     */
    @PostMapping("/seasonSearch")
    @ApiOperation(value = "季度查询动漫列表", notes = "根据季度查询动漫列表信息")
    @ApiImplicitParam(value = "查询条件", name = "date", required = true, dataType = "Date", paramType = "body")
    public ResponseData<Wiki_Animation> animListSeasonQuery(@RequestBody Date date) {
        List<Wiki_Animation> animList = animationService.getAnimBySeason(date);
        if (animList != null) {
            return ResponseData.success().putDataVule("animList", animList);
        } else {
            return ResponseData.serverInternalError();
        }
    }

    /**
     * 全文检索
     *
     * @param str
     * @return
     */
    @PostMapping("/fullTextSearch")
    @ApiOperation(value = "全文检索动漫列表", notes = "全文检索动漫列表信息")
    @ApiImplicitParam(value = "查询条件", name = "str", required = false, paramType = "query")
    public ResponseData<Wiki_Animation> fullTextQuery(@RequestParam String str) {
        List<Wiki_Animation> animList = animationService.fullTextSearch(str);
        if (animList != null) {
            return ResponseData.success().putDataVule("animList", animList);
        } else {
            return ResponseData.serverInternalError();
        }
    }

    /**
     * 分页查询动漫列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/animListPage")
    @ApiOperation(value = "分页查询动漫列表", notes = "查询分页动漫列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name = "pageNum", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = true, dataType = "Integer", paramType = "query")
    })
    public ResponseData<Wiki_Animation> animListPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<Wiki_Animation> animationIPage = animationService.getAnimListPage(pageNum, pageSize);
        return ResponseData.success().putDataVule("total", animationIPage.getTotal())
                .putDataVule("pages", animationIPage.getPages())
                .putDataVule("animList", animationIPage.getRecords());
    }

    /**
     * 根据动漫名以及标签分页查询列表
     *
     * @param pageNum
     * @param pageSize
     * @param queryMap
     * @return
     */
    @PostMapping("/nameLabelPage")
    @ApiOperation(value = "分页动漫名标签条件查询", notes = "分页动漫名标签条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name = "pageNum", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "查询条件", name = "queryMap", required = false, dataType = "Map", paramType = "body")
    })
    public ResponseData<Wiki_Animation> nameLabelPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody Map<String, Object> queryMap) {
        IPage<Wiki_Animation> animationIPage = animationService.getAnimByNameAndLabelPage(pageNum, pageSize, queryMap);
        return ResponseData.success().putDataVule("total", animationIPage.getTotal())
                .putDataVule("pages", animationIPage.getPages())
                .putDataVule("animList", animationIPage.getRecords());
    }

    /**
     * 根据季度分页查询列表
     *
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    @PostMapping("/seasonPage")
    @ApiOperation(value = "分页季度条件查询", notes = "分页季度条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name = "pageNum", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "查询条件", name = "date", required = true, dataType = "Date", paramType = "body")
    })
    public ResponseData<Wiki_Animation> seasonPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody Date date) {
        IPage<Wiki_Animation> animationIPage = animationService.getAnimBySeasonPage(pageNum, pageSize, date);
        return ResponseData.success().putDataVule("total", animationIPage.getTotal())
                .putDataVule("pages", animationIPage.getPages())
                .putDataVule("animList", animationIPage.getRecords());
    }

    /**
     * 全文检索分页查询列表
     *
     * @param pageNum
     * @param pageSize
     * @param str
     * @return
     */
    @PostMapping("/fullTextPage")
    @ApiOperation(value = "分页全文检索", notes = "分页全文检索")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页数", name = "pageNum", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "每页条数", name = "pageSize", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "查询条件", name = "str", required = false, paramType = "query")
    })
    public ResponseData<Wiki_Animation> fullTextPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String str) {
        IPage<Wiki_Animation> animationIPage = animationService.fullTextSearchPage(pageNum, pageSize, str);
        return ResponseData.success().putDataVule("total", animationIPage.getTotal())
                .putDataVule("pages", animationIPage.getPages())
                .putDataVule("animList", animationIPage.getRecords());
    }

    /**
     * 添加动漫信息
     *
     * @param animation
     * @return
     */
    @PostMapping("/addAnimation")
    @ApiOperation(value = "添加动漫信息", notes = "添加动漫信息")
    @ApiImplicitParam(value = "动漫信息", name = "animation", required = true, dataType = "Wiki_Animation", paramType = "body")
    public ResponseData<Wiki_Animation> addAnimation(@RequestBody Wiki_Animation animation) {
        Integer success = animationService.addAnimationInfo(animation);
        return ResponseData.success().putDataVule("success", success)
                .putDataVule("animation", animation);
    }

    /**
     * 修改动漫信息
     * @param animation
     * @return
     */
    @PostMapping("/modifyAnimation")
    @ApiOperation(value = "修改动漫信息", notes = "修改动漫信息")
    @ApiImplicitParam(value = "动漫信息", name = "animation", required = true, dataType = "Wiki_Animation", paramType = "body")
    public ResponseData<Wiki_Animation> modifyAnimation(@RequestBody Wiki_Animation animation) {
        Integer success = animationService.modifyAnimationInfo(animation);
        return ResponseData.success().putDataVule("success",success)
                .putDataVule("animation",animation);
    }

    /**
     * 删除动漫信息
     * @param id
     * @return
     */
    @PostMapping("/deleteAnimation")
    @ApiOperation(value = "删除动漫信息", notes = "删除动漫信息")
    @ApiImplicitParam(value = "删除动漫ID", name = "id", required = true, dataType = "Integer", paramType = "query")
    public ResponseData<Wiki_Animation> deleteAnimation(@RequestParam Integer id) {
        Wiki_Animation animation=animationService.getAnimById(id);
        Integer success = animationService.deleteAnimation(id);
        return ResponseData.success().putDataVule("success",success)
                .putDataVule("animation",animation);
    }
}

