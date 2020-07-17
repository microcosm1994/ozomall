package com.ozomall.controller.admin;

import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminClassifyService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "商品分类接口")
@Controller
@RequestMapping("/admin/classify")
@ResponseBody
public class ClassifyController {
    @Resource
    private AdminClassifyService adminClassifyService;

    @ApiOperation("添加商品分类")
    @PostMapping("/add")
    public Result addClassify(@RequestBody AdminClassifyDto form) {
        if (!StringUtils.isEmpty(form.getName()) && !StringUtils.isEmpty(form.getClassifyLevel())) {
            return adminClassifyService.addClassify(form);
        } else {
            return ResultGenerate.genErroResult("分类等级与分类名称不能为空");
        }
    }

    @ApiOperation("获取分类列表")
    @GetMapping("/list")
    public Result queryClassify(AdminClassifyDto form) {
        return adminClassifyService.queryClassify(form);
    }

}
