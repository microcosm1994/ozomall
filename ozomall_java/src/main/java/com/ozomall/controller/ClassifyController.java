package com.ozomall.controller;

import com.ozomall.entity.ClassifyDto;
import com.ozomall.entity.Result;
import com.ozomall.service.ClassifyService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "商品分类接口")
@Controller
@RequestMapping("/classify")
@ResponseBody
public class ClassifyController {
    @Resource
    private ClassifyService classifyService;

    @ApiOperation("添加商品分类")
    @PostMapping("/add")
    public Result addClassify(@RequestBody ClassifyDto form) {
        if (!StringUtils.isEmpty(form.getName()) && !StringUtils.isEmpty(form.getClassifyLevel())) {
            return classifyService.addClassify(form);
        } else {
            return ResultGenerate.genErroResult("分类等级与分类名称不能为空");
        }
    }

    @ApiOperation("获取分类列表")
    @GetMapping("/list")
    public Result queryClassify(ClassifyDto form) {
        return classifyService.queryClassify(form);
    }

    @ApiOperation("获取2、3级分类列表")
    @GetMapping("/childrenList")
    public Result queryChildrenList(ClassifyDto form) {
        return classifyService.queryChildrenList(form);
    }

}
