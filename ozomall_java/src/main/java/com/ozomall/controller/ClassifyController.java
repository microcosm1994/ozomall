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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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

    @ApiOperation("上传商品分类图片")
    @PostMapping("/upload")
    public Result classifyUpload(@RequestBody MultipartFile file) throws IOException {
        return classifyService.classifyUpload(file);
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

    @ApiOperation("修改商品分类")
    @PostMapping("/put")
    public Result putClassify(@RequestBody ClassifyDto form) {
        return classifyService.putClassify(form);
    }

}
